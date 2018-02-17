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

import fr.litarvan.sakado.server.SakadoServer;
import fr.litarvan.sakado.server.http.Controller;
import fr.litarvan.sakado.server.http.error.APIError;
import fr.litarvan.sakado.server.pronote.Cours;
import fr.litarvan.sakado.server.pronote.Pronote;
import fr.litarvan.sakado.server.pronote.PronoteLinks;
import fr.litarvan.sakado.server.pronote.User;
import fr.litarvan.sakado.server.pronote.Week;
import fr.litarvan.sakado.server.util.CalendarUtils;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import javax.inject.Inject;
import spark.Request;
import spark.Response;

public class GraphQLController extends Controller
{
    @Inject
    private Pronote pronote;

    @Inject
    private PronoteLinks links;

    public Object graphql(Request request, Response response) throws APIError, URISyntaxException
    {
        String query = require(request, "query");
        ExecutionResult result = get().execute(query);

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
            .type("Query", builder -> builder.dataFetcher("user", environment -> pronote.getByToken(environment.getArgument("token")))
                                             .dataFetcher("links", environment -> links.all()))
            .type("User", builder -> builder.dataFetcher("nextCours", environment -> getNextCours(environment.getSource()))
                                            .dataFetcher("away", environment -> getAway(environment.getSource()))).build();

        SchemaGenerator generator = new SchemaGenerator();
        GraphQLSchema schema = generator.makeExecutableSchema(registry, wiring);

        return GraphQL.newGraphQL(schema).build();
    }

    public Cours getNextCours(User user)
    {
        Calendar current = CalendarUtils.create();
        current.add(Calendar.MINUTE, -30);

        System.out.println("Processing next cours, current : " + CalendarUtils.parse(current, Calendar.HOUR_OF_DAY, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND));

        Cours next = null;

        for (Cours cours : user.getEDT()[0].getContent())
        {
            if (cours.getFrom().after(current))
            {
                next = cours;
                System.out.println("Next found : " + CalendarUtils.parse(cours.getFrom(), Calendar.HOUR_OF_DAY, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND));
                break;
            }
        }

        if (next == null)
        {
            next = user.getEDT()[1].getContent()[0];
        }

        return next;
    }

    public Week[] getAway(User user)
    {
        Week[] edt = user.getEDT();
        Week[] result = new Week[edt.length];

        for (int i = 0; i < edt.length; i++)
        {
            Week week = edt[i];
            List<Cours> away = new ArrayList<>();

            for (Cours cours : week.getContent())
            {
                if (cours.isAway())
                {
                    away.add(cours);
                }
            }

            result[i] = week.cloneWith(away.toArray(new Cours[away.size()]));
        }

        return result;
    }
}
