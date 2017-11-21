package fr.litarvan.sakado.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.litarvan.commons.App;
import fr.litarvan.commons.config.ConfigProvider;
import fr.litarvan.commons.crash.ExceptionHandler;
import fr.litarvan.commons.io.IOSource;
import fr.litarvan.sakado.server.http.Routes;
import fr.litarvan.sakado.server.http.error.APIError;
import fr.litarvan.sakado.server.http.error.HTTPReportField;
import fr.litarvan.sakado.server.http.error.InRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Spark;

import javax.inject.Inject;
import java.io.File;
import java.net.InetSocketAddress;

public class SakadoServer implements App
{
    public static final String VERSION = "1.0.0";

    private static final Logger log = LogManager.getLogger("SakadoServer");

    @Inject
    private Gson gson;

    @Inject
    private ExceptionHandler exceptionHandler;

    @Inject
    private ConfigProvider configs;

    @Inject
    private Routes routes;

    @Override
    public void start()
    {
        log.info("Starting Sakado Server v{}", getVersion());

        exceptionHandler.addField(new HTTPReportField("URL", Request::url));
        exceptionHandler.addField(new HTTPReportField("Scheme", Request::scheme));
        exceptionHandler.addField(new HTTPReportField("Route", (request) -> request.requestMethod() + " " + request.pathInfo()));
        exceptionHandler.addField(new HTTPReportField("Route params", (request) -> request.params().toString()));
        exceptionHandler.addField(new HTTPReportField("Query params", (request) -> request.queryParams().toString()));

        // Configs
        log.info("Loading configs...");

        configs.from("config/app.json").defaultIn(IOSource.at("app.default.json"));

        Spark.port(configs.at("app.port", int.class));
        Spark.notFound((request, response) -> {
            response.type("application/json");

            JsonObject obj = new JsonObject();
            obj.addProperty("title", APIError.NOT_FOUND);
            obj.addProperty("message", "Can't find route '" + request.uri() + "'");

            return gson.toJson(obj);
        });

        // Exception handling
        Spark.exception(Exception.class, (e, request, response) ->
        {
            response.type("application/json; charset=utf-8");

            JsonObject rep = new JsonObject();
            rep.addProperty("success", false);

            if (e instanceof APIError)
            {
                rep.addProperty("title", ((APIError) e).getTitle());
            }
            else
            {
                rep.addProperty("type", e.getClass().getName());
            }

            rep.addProperty("message", e.getMessage());

            response.body(gson.toJson(rep));

            if (!(e instanceof APIError))
            {
                exceptionHandler.handle(new InRequestException(e, request));
            }
        });

        // Loading routes
        routes.load();

        // Waiting for spark...
        Spark.awaitInitialization();

        System.out.println();
        log.info("====> Done ! Sakado Server started on " + new InetSocketAddress(Spark.port()));
        System.out.println();
    }

    @Override
    public String getName()
    {
        return "Sakado Server";
    }

    @Override
    public String getVersion()
    {
        return VERSION;
    }

    @Override
    public File getFolder()
    {
        return new File("./");
    }
}
