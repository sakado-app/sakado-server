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
package fr.litarvan.sakado.server.data;

import fr.litarvan.sakado.server.StudentClass;
import fr.litarvan.sakado.server.data.network.RequestException;
import fr.litarvan.sakado.server.push.PushService;
import fr.litarvan.sakado.server.push.PushType;
import fr.litarvan.sakado.server.util.CalendarUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;
import javax.inject.Inject;

public class RefreshService
{
    public static final long RATE = 15 * 60 * 1000;

    private static final Logger log = LogManager.getLogger("RefreshService");

    @Inject
    private SakadoData data;

    @Inject
    private PushService push;

    @Inject
    private UserManager userManager;

    private List<String> seen;

    public RefreshService()
    {
        this.seen = new ArrayList<>();
    }

    public void start()
    {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                for (Establishment establishment : data.getEstablishments())
                {
                    for (StudentClass studentClass : establishment.getClasses())
                    {
                        studentClass.getLoggedUsers().forEach(RefreshService.this::refresh);
                    }
                }
            }
        }, RATE, RATE);
    }

    protected synchronized void refresh(User user)
    {
        try
        {
            user.update();
        }
        catch (IOException | RequestException e)
        {
            if (e instanceof RequestException && e.getMessage().contains("Can't find session with token"))
            {
                log.error("Deleting ghost session '" + user.getName() + "' (" + user.getToken() + ")");
                userManager.remove(user);

                return;
            }

            log.error("Unknown error while refreshing data from UserManager, ignoring", e);
        }

        try
        {
            this.checkNewAway(user);
            this.checkNewMark(user);

            if (user.getHomeworks() != null)
            {
                this.checkLongHomeworks(user);
            }
        }
        catch (Exception e)
        {
            log.error("Error during refresh process", e);
        }
    }

    protected void checkNewAway(User user)
    {
        List<Lesson> away = new ArrayList<>();
        for (Week week : user.getTimetable())
        {
            for (Lesson lesson : week.getContent())
            {
                if (lesson.isAway())
                {
                    away.add(lesson);
                }
            }
        }

        away.removeIf(c -> {
            String id = user.getUsername() + "-" + c.getId();

            if (!seen.contains(id))
            {
                seen.add(id);
                return false;
            }

            return true;
        });

        if (away.size() == 0)
        {
            return;
        }

        String title = "Sakado - ";
        String message;

        if (away.size() > 1)
        {
            title += "Plusieurs profs. absents";
            message = "Cliquer pour voir";
        }
        else
        {
            title += "Prof. absent";

            Lesson lesson = away.get(0);
            Calendar day = lesson.getFromAsCalendar();

            int start = day.get(Calendar.HOUR_OF_DAY);

            message = lesson.getTeacher();
            message += " : " + CalendarUtils.parse(day, Calendar.DAY_OF_WEEK, Calendar.DAY_OF_MONTH, Calendar.MONTH);
            message += " - " + start + "h-" + CalendarUtils.parse(lesson.getToAsCalendar(), Calendar.HOUR_OF_DAY) + "h";
        }

        try
        {
            push.send(user, PushType.AWAY, title, message);
        }
        catch (Exception e)
        {
            log.error("Couldn't send away push notification", e);
        }
    }

    protected void checkNewMark(User user)
    {
        Calendar max = CalendarUtils.create();
        max.add(Calendar.DAY_OF_MONTH, -2);

        List<Mark> marks = new ArrayList<>();

        for (Mark mark : user.getLastMarks())
        {
            if (mark.getTimeAsCalendar().after(max))
            {
                marks.add(mark);
            }
        }

        marks.removeIf(m -> {
            String id = user.getUsername() + "-" + m.getId();

            if (!seen.contains(id))
            {
                seen.add(id);
                return false;
            }

            return true;
        });

        if (marks.size() == 0)
        {
            return;
        }

        String title = "Sakado - ";
        String message;

        if (marks.size() > 1)
        {
            title += "Nouvelles notes";
            message = "Cliquer pour voir";
        }
        else
        {
            title += "Nouvelle note";
            message = marks.get(0).getSubject() + " - " + marks.get(0).getMark();
        }

        try
        {
            push.send(user, PushType.MARK, title, message);
        }
        catch (Exception e)
        {
            log.error("Couldn't send away push notification", e);
        }
    }

    protected void checkLongHomeworks(User user)
    {
        List<Homework> longHomeworks = new ArrayList<>();
        Calendar current = CalendarUtils.create();

        for (Homework homework : user.getHomeworks())
        {
            if (!user.studentClass().getLongHomeworks().contains(homework.getId()))
            {
                continue;
            }

            Calendar date = homework.getTimeAsCalendar();
            date.add(Calendar.HOUR_OF_DAY, -6); // 18h, the last day before

            if (current.after(date))
            {
                longHomeworks.add(homework);
            }
        }

        longHomeworks.removeIf(h -> {
            String id = user.getUsername() + "-" + h.getId();

            if (!seen.contains(id))
            {
                seen.add(id);
                return false;
            }

            return true;
        });

        if (longHomeworks.size() == 0)
        {
            return;
        }

        String title = "Sakado - ";
        String message;

        if (longHomeworks.size() > 1)
        {
            title += longHomeworks.size() + " longs devoirs pour demain";
            message = "Cliquer pour voir";
        }
        else
        {
            title += "Long devoir pour demain";
            message = longHomeworks.get(0).getSubject() + " - " + longHomeworks.get(0).getContent();
        }

        try
        {
            push.send(user, PushType.HOMEWORK, title, message);
        }
        catch (Exception e)
        {
            log.error("Couldn't send away push notification", e);
        }
    }

    protected String getID(User user, Mark mark)
    {
        return user.getUsername() + "-" + mark.getMark() + "-" + mark.getSubject() + "-" + mark.getTimeAsCalendar().get(Calendar.DAY_OF_MONTH) + "-" + mark.getTimeAsCalendar().get(Calendar.MONTH);
    }

    protected String getID(User user, Lesson lesson)
    {
        return user.getUsername() + "-" + lesson.getFromAsCalendar().getTimeInMillis();
    }
}