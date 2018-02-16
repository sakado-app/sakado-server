package fr.litarvan.sakado.server.http.controller;

import fr.litarvan.sakado.server.SakadoServer;
import fr.litarvan.sakado.server.http.Controller;
import fr.litarvan.sakado.server.http.error.APIError;
import graphql.GraphQL;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import spark.Request;
import spark.Response;

import java.io.File;
import java.net.URISyntaxException;

public class GraphQLController extends Controller
{
    public Object graphql(Request request, Response response) throws APIError
    {
        String query = require(request, "query");


    }

    public GraphQL get() throws URISyntaxException
    {
        SchemaParser parser = new SchemaParser();
        TypeDefinitionRegistry registry = parser.parse(new File(SakadoServer.class.getResource("/schema.graphql").toURI()));

        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("user", ))
                .build();


    }
}
