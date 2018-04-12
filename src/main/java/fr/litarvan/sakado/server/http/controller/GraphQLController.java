/*
 *  Sakado, an app for school
 *  Copyright (c) 2017-2018 Adrien 'Litarvan' Navratil
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package fr.litarvan.sakado.server.http.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import fr.litarvan.commons.config.ConfigProvider;
import fr.litarvan.sakado.server.SakadoServer;
import fr.litarvan.sakado.server.data.StudentClass;
import fr.litarvan.sakado.server.data.holiday.DayHoliday;
import fr.litarvan.sakado.server.data.holiday.NextHolidays;
import fr.litarvan.sakado.server.data.holiday.PeriodHoliday;
import fr.litarvan.sakado.server.data.holiday.SavedDayHoliday;
import fr.litarvan.sakado.server.data.holiday.SavedPeriodHoliday;
import fr.litarvan.sakado.server.http.Controller;
import fr.litarvan.sakado.server.http.error.APIError;
import fr.litarvan.sakado.server.data.*;
import fr.litarvan.sakado.server.push.PushService;
import fr.litarvan.sakado.server.push.PushType;
import fr.litarvan.sakado.server.util.CalendarUtils;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;

import static java.util.Calendar.*;

public class GraphQLController extends Controller
{
    private static final Logger log = LogManager.getLogger("GraphQL");

    @Inject
    private UserManager userManager;

    @Inject
    private SakadoData data;

    @Inject
    private PushService push;

    @Inject
    private ConfigProvider config;

    private GraphQL schema;

    public Object graphql(Request request, Response response) throws APIError
    {
        User user = userManager.getByToken(request.headers("Token"));

        if (this.schema == null || "true".equals(System.getProperty("sakado.debug"))) // Live schema edit support
        {
            this.schema = get(request);
        }

        String query = require(request, "query");
        ExecutionResult result = this.schema.execute(ExecutionInput.newExecutionInput().query(query).context(user).build());

        if (result.getErrors().size() > 0)
        {
            return json(result.getErrors(), response);
        }

        return json(result.getData(), response);
    }

    public GraphQL get(Request request)
    {
        SchemaParser parser = new SchemaParser();
        TypeDefinitionRegistry registry = parser.parse(new InputStreamReader(SakadoServer.class.getResourceAsStream("/schema.graphql")));

        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
            .type("Query", builder -> builder.dataFetcher("user", DataFetchingEnvironment::getContext)
                                             .dataFetcher("establishments", environment -> getEstablishments()))
            .type("Mutation", builder -> builder.dataFetcher("user", DataFetchingEnvironment::getContext))
            .type("User", builder -> builder.dataFetcher("admin", environment -> isAdmin(environment.getSource()))
                                            .dataFetcher("representative", environment -> isRepresentative(environment.getSource()))
                                            .dataFetcher("nextLesson", environment -> getNextLesson(environment.getSource()))
                                            .dataFetcher("tomorrow", environment -> getTomorrow(environment.getSource()))
                                            .dataFetcher("away", environment -> getAway(environment.getSource()))
                                            .dataFetcher("homeworksEnabled", environment -> areHomeworksEnabled(environment.getSource()))
                                            .dataFetcher("class", environment -> getStudentClass(environment.getSource()))
                                            .dataFetcher("holidays", environment -> getNextHolidays(environment.getSource())))
            .type("MutableUser", builder -> builder.dataFetcher("homework", environment -> getHomework(environment.getContext(), environment.getArgument("id")))
                                            .dataFetcher("class", environment -> getStudentClass(environment.getContext()))
                                            .dataFetcher("addReminder", environment -> addReminder(environment.getContext(), environment.getArgument("title"), environment.getArgument("content"), environment.getArgument("time"), false))
                                            .dataFetcher("removeReminder", environment -> removeReminder(environment.getContext(), environment.getArgument("title"), false)))
            .type("MutableStudentClass", builder -> builder.dataFetcher("addRepresentative", environment -> addRepresentative(environment.getContext(), environment.getArgument("username")))
                                            .dataFetcher("removeRepresentative", environment -> removeRepresentative(environment.getContext(), environment.getArgument("username")))
                                            .dataFetcher("addReminder", environment -> addReminder(environment.getContext(), environment.getArgument("title"), environment.getArgument("content"), environment.getArgument("time"), true))
                                            .dataFetcher("removeReminder", environment -> removeReminder(environment.getContext(), environment.getArgument("title"), true))
                                            .dataFetcher("notify", environment -> notify(environment.getSource(), environment.getArgument("content"))))
            .type("Homework", builder -> builder.dataFetcher("long", environment -> isLong(environment.getContext(), environment.getSource())))
            .type("MutableHomework", builder -> builder.dataFetcher("long", environment -> setLong(environment.getContext(), environment.getSource(), environment.getArgument("long"))))
            .build();

        SchemaGenerator generator = new SchemaGenerator();
        GraphQLSchema schema = generator.makeExecutableSchema(registry, wiring);

        return GraphQL.newGraphQL(schema).build();
    }

    public String[] getEstablishments()
    {
        String[] establishment = new String[data.getEstablishments().length];

        for (int i = 0; i < data.getEstablishments().length; i++)
        {
            establishment[i] = data.getEstablishments()[i].getName();
        }

        return establishment;
    }

    public boolean isAdmin(User user)
    {
        return user.studentClass().getAdmin().equalsIgnoreCase(user.getUsername());
    }

    public boolean isRepresentative(User user)
    {
        return user.studentClass().getRepresentatives().contains(user.getUsername());
    }

    public Lesson[] getNextLesson(User user)
    {
        Calendar current = CalendarUtils.create();
        current.add(Calendar.MINUTE, -30);

        Lesson[] next = null;

        Lesson[] content = user.getTimetable()[0].getContent();
        for (int i = 0; i < content.length; i++)
        {
            Lesson lesson = content[i];
            if (lesson.getFromAsCalendar().after(current) && !lesson.isAway() && !lesson.isCancelled())
            {
                next = new Lesson[]{i != 0 ? content[i - 1] : null, lesson, i + 1 < content.length ? content[i + 1] : null};
                break;
            }
        }

        if (next == null)
        {
            next = new Lesson[] {null, user.getTimetable()[1].getContent()[0], user.getTimetable()[1].getContent()[1] };
        }

        return next;
    }

    public Tomorrow getTomorrow(User user)
    {
        Calendar tomorrowDay = CalendarUtils.create();

        if (tomorrowDay.get(Calendar.HOUR_OF_DAY) >= 15)
        {
            tomorrowDay.add(DAY_OF_MONTH, 1);
            tomorrowDay.add(HOUR_OF_DAY, -3); // For late people
        }

        if (tomorrowDay.get(DAY_OF_WEEK) == Calendar.SUNDAY)
        {
            tomorrowDay.add(DAY_OF_MONTH, 1);
        }

        List<Lesson> timetable = new ArrayList<>();
        for (Lesson lesson : user.getTimetable()[CalendarUtils.create().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? 1 : 0].getContent())
        {
            if (CalendarUtils.isSameDay(tomorrowDay, lesson.getFromAsCalendar()))
            {
                timetable.add(lesson);
            }
        }

        List<Reminder> reminders = new ArrayList<>();
        reminders.addAll(user.getReminders().stream().filter(reminder -> CalendarUtils.isSameDay(tomorrowDay, reminder.getTimeAsCalendar())).collect(Collectors.toList()));
        reminders.addAll(user.studentClass().getReminders().stream().filter(reminder -> CalendarUtils.isSameDay(tomorrowDay, reminder.getTimeAsCalendar())).collect(Collectors.toList()));

        List<Homework> homeworks = new ArrayList<>();
        for (Homework homework : user.getHomeworks())
        {
            if (CalendarUtils.isSameDay(tomorrowDay, homework.getUntilAsCalendar()))
            {
                homeworks.add(homework);
            }
        }
        return new Tomorrow(tomorrowDay.getTimeInMillis(), timetable.toArray(new Lesson[]{}), reminders.toArray(new Reminder[]{}), homeworks.toArray(new Homework[]{}));
    }

    public Week[] getAway(User user)
    {
        Week[] edt = user.getTimetable();
        Week[] result = new Week[edt.length];

        for (int i = 0; i < edt.length; i++)
        {
            Week week = edt[i];
            List<Lesson> away = new ArrayList<>();

            for (Lesson lesson : week.getContent())
            {
                if (lesson.isAway() || lesson.isCancelled())
                {
                    away.add(lesson);
                }
            }

            result[i] = week.cloneWith(away.toArray(new Lesson[away.size()]));
        }

        return result;
    }

    public boolean isLong(User user, Homework homework)
    {
        return user.studentClass().getLongHomeworks().contains(homework.getId());
    }

    public boolean setLong(User user, Homework homework, boolean isLong)
    {
        user.studentClass().setLongHomework(homework.getId(), isLong);
        return isLong;
    }

    public StudentClass getStudentClass(User user)
    {
        return user.studentClass();
    }

    public String addRepresentative(User user, String username)
    {
        StudentClass theClass = user.studentClass();

        if (!theClass.getAdmin().equalsIgnoreCase(user.getUsername()))
        {
            throw new RuntimeException("You can't do this without being admin");
        }

        theClass.getRepresentatives().add(username);

        return username;
    }

    public String removeRepresentative(User user, String username)
    {
        StudentClass theClass = user.studentClass();

        if (!theClass.getAdmin().equalsIgnoreCase(user.getUsername()))
        {
            throw new RuntimeException("You can't do this without being admin");
        }

        theClass.getRepresentatives().remove(username);

        return username;
    }

    public Homework getHomework(User user, String id)
    {
        for (Homework homework : user.getHomeworks())
        {
            if (homework.getId().equals(id))
            {
                return homework;
            }
        }

        return null;
    }

    public boolean areHomeworksEnabled(User user)
    {
        return user.getHomeworks() != null;
    }

    public Reminder addReminder(User user, String title, String content, long time, boolean studentClass)
    {
        Reminder reminder = new Reminder(title, content, user.getName(), time);
        (studentClass ? user.studentClass().getReminders() : user.getReminders()).add(reminder);

        return reminder;
    }

    public Reminder removeReminder(User user, String title, boolean studentClass)
    {
        List<Reminder> reminders = studentClass ? user.studentClass().getReminders() : user.getReminders();
        for (Reminder reminder : reminders)
        {
            if (reminder.getTitle().equals(title))
            {
                if (studentClass && !isAdmin(user) && !isRepresentative(user) && !reminder.getAuthor().equals(user.getUsername()))
                {
                    throw new RuntimeException("You can't do that without being either admin or the reminder author");
                }

                reminders.remove(reminder);
                return reminder;
            }
        }

        return null;
    }

    public String notify(StudentClass studentClass, String content)
    {
        for (User user : studentClass.getLoggedUsers())
        {
            try
            {
                push.send(user, PushType.NOTIFY, "Information", content);
            }
            catch (IOException e)
            {
                log.error("Couldn't send global notification to user '" + user.getUsername() + "'", e);
            }
        }

        return content;
    }

    public NextHolidays getNextHolidays(User user)
    {
        Calendar today = CalendarUtils.create();

        SavedDayHoliday[] dayHolidays = config.at("holidays.days", SavedDayHoliday[].class);
        SavedPeriodHoliday[] periodHolidays = config.at("holidays.periods", SavedPeriodHoliday[].class);

        DayHoliday nextDayHoliday = null;
        PeriodHoliday nextPeriodHoliday = null;

        for (SavedDayHoliday holiday : dayHolidays)
        {
            Calendar time = CalendarUtils.create();
            time.set(Calendar.DAY_OF_MONTH, holiday.getDay());
            time.set(Calendar.MONTH, holiday.getMonth() - 1);

            if (time.after(today) && (nextDayHoliday == null || time.before(CalendarUtils.fromTimestamp(nextDayHoliday.getTime()))))
            {
                nextDayHoliday = new DayHoliday(holiday.getName(), time.getTimeInMillis());
            }
        }

        for (SavedPeriodHoliday holiday : periodHolidays)
        {
            Calendar from = CalendarUtils.create();
            from.set(Calendar.DAY_OF_MONTH, holiday.getFrom().getDay());
            from.set(Calendar.MONTH, holiday.getFrom().getMonth() - 1);

            Calendar to = CalendarUtils.create();
            to.set(Calendar.DAY_OF_MONTH, holiday.getTo().getDay());
            to.set(Calendar.MONTH, holiday.getTo().getMonth() - 1);

            if (from.after(today) && (nextPeriodHoliday == null || from.before(CalendarUtils.fromTimestamp(nextPeriodHoliday.getFrom()))))
            {
                nextPeriodHoliday = new PeriodHoliday(holiday.getName(), from.getTimeInMillis(), to.getTimeInMillis());
            }
        }

        long untilDay = 0;
        long untilPeriod = 0;

        if (nextDayHoliday != null)
        {
            untilDay = (long) Math.ceil((float) ((nextDayHoliday.getTime() - today.getTimeInMillis()) / 1000 / 60 / 60 ) / 24f);
        }

        if (nextPeriodHoliday != null)
        {
            untilPeriod = (long) Math.ceil((float) ((nextPeriodHoliday.getFrom() - today.getTimeInMillis()) / 1000 / 60 / 60 ) / 24f);
        }

        return new NextHolidays(nextDayHoliday, untilDay, nextPeriodHoliday, untilPeriod);
    }
}
