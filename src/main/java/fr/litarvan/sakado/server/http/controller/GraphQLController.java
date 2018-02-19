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

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.litarvan.sakado.server.ClassManager;
import fr.litarvan.sakado.server.SakadoServer;
import fr.litarvan.sakado.server.http.Controller;
import fr.litarvan.sakado.server.http.error.APIError;
import fr.litarvan.sakado.server.pronote.*;
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

public class GraphQLController extends Controller
{
    private static final Logger log = LogManager.getLogger("GraphQL");

    @Inject
    private Pronote pronote;

    @Inject
    private PronoteLinks links;

    @Inject
    private ClassManager classManager;

    private GraphQL schema;

    public Object graphql(Request request, Response response) throws APIError, URISyntaxException
    {
        User user = pronote.getByToken(request.headers("Token"));

        if (this.schema == null)
        {
            this.schema = get();
        }

        String query = require(request, "query");
        ExecutionResult result = this.schema.execute(ExecutionInput.newExecutionInput().query(query).context(user).build());

        if (result.getErrors().size() > 0)
        {
            return json(result.getErrors(), response);
        }

        return json(result.getData(), response);
    }

    public GraphQL get() throws URISyntaxException
    {
        SchemaParser parser = new SchemaParser();
        TypeDefinitionRegistry registry = parser.parse(new File(SakadoServer.class.getResource("/schema.graphql").toURI()));

        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
            .type("Query", builder -> builder.dataFetcher("user", DataFetchingEnvironment::getContext)
                                             .dataFetcher("links", environment -> links.all()))
            .type("Mutation", builder -> builder.dataFetcher("user", DataFetchingEnvironment::getContext))
            .type("User", builder -> builder.dataFetcher("admin", environment -> isAdmin(environment.getSource()))
                                            .dataFetcher("representative", environment -> isRepresentative(environment.getSource()))
                                            .dataFetcher("nextLesson", environment -> getNextLesson(environment.getSource()))
                                            .dataFetcher("away", environment -> getAway(environment.getSource())))
            .type("MutableUser", builder -> builder.dataFetcher("homework", environment -> getHomework(environment.getContext(), environment.getArgument("id"))))
            .type("Homework", builder -> builder.dataFetcher("long", environment -> isLong(environment.getContext(), environment.getSource())))
            .type("MutableHomework", builder -> builder.dataFetcher("long", environment -> setLong(environment.getContext(), environment.getSource(), environment.getArgument("long"))))
            .build();

        SchemaGenerator generator = new SchemaGenerator();
        GraphQLSchema schema = generator.makeExecutableSchema(registry, wiring);

        return GraphQL.newGraphQL(schema).build();
    }

    public boolean isAdmin(User user)
    {
        return classManager.of(user).getAdmin().equalsIgnoreCase(user.getUsername());
    }

    public boolean isRepresentative(User user)
    {
        return classManager.of(user).getRepresentatives().contains(user.getUsername());
    }

    public Lesson getNextLesson(User user)
    {
        Calendar current = CalendarUtils.create();
        current.add(Calendar.MINUTE, -30);

        log.info("Processing next lesson, current : " + CalendarUtils.parse(current, Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND));

        Lesson next = null;

        for (Lesson lesson : user.getTimetable()[0].getContent())
        {
            if (lesson.getFromAsCalendar().after(current))
            {
                next = lesson;
                log.info("Next found : " + CalendarUtils.parse(lesson.getFromAsCalendar(), Calendar.DAY_OF_MONTH, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND));

                break;
            }
        }

        if (next == null)
        {
            next = user.getTimetable()[1].getContent()[0];
        }

        return next;
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
                if (lesson.isAway())
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
        return classManager.of(user).getLongHomeworks().contains(homework.getId());
    }

    public boolean setLong(User user, Homework homework, boolean isLong)
    {
        classManager.of(user).setLongHomework(homework.getId(), isLong);
        return isLong;
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
}
