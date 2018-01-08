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
package fr.litarvan.sakado.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.litarvan.commons.App;
import fr.litarvan.commons.config.ConfigProvider;
import fr.litarvan.commons.crash.ExceptionHandler;
import fr.litarvan.commons.io.IOSource;
import fr.litarvan.sakado.server.http.Controller;
import fr.litarvan.sakado.server.http.Routes;
import fr.litarvan.sakado.server.http.error.APIError;
import fr.litarvan.sakado.server.http.error.HTTPReportField;
import fr.litarvan.sakado.server.http.error.InRequestException;
import fr.litarvan.sakado.server.pronote.Pronote;
import fr.litarvan.sakado.server.pronote.RefreshService;
import fr.litarvan.sakado.server.pronote.network.RequestException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Filter;
import spark.Request;
import spark.Spark;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

public class SakadoServer implements App
{
    public static final String VERSION = "0.0.2";

    private static final Logger log = LogManager.getLogger("SakadoServer");

    @Inject
    private Gson gson;

    @Inject
    private ExceptionHandler exceptionHandler;

    @Inject
    private ConfigProvider configs;

    @Inject
    private Pronote pronote;

    @Inject
    private Routes routes;

    @Inject
    private RefreshService refresh;

    @Inject
    private ClasseManager classeManager;

    @Override
    public void start()
    {
        log.info("Starting Sakado Server v{}", getVersion());

        exceptionHandler.addField(new HTTPReportField("URL", Request::url));
        exceptionHandler.addField(new HTTPReportField("Scheme", Request::scheme));
        exceptionHandler.addField(new HTTPReportField("Route", (request) -> request.requestMethod() + " " + request.pathInfo()));
        exceptionHandler.addField(new HTTPReportField("Route params", (request) -> request.params().toString()));
        exceptionHandler.addField(new HTTPReportField("Query params", (request) -> request.queryParams().toString()));

        log.info("Loading configs...");

        configs.from("config/app.json").defaultIn(IOSource.at("app.default.json"));
        configs.from("config/proxy.json").defaultIn(IOSource.at("proxy.default.json"));
        configs.from("config/pronote.json").defaultIn(IOSource.at("pronote.default.json"));
        configs.from("config/fcm.json").defaultIn(IOSource.at("fcm.default.json"));

        if (configs.at("proxy.enabled", boolean.class))
        {
            log.info("Setting up dynamic proxy...");

            try
            {
                String address = configs.at("proxy.address");

                if (!address.endsWith("/"))
                {
                    address += "/";
                }

                HttpURLConnection conn = (HttpURLConnection) new URL(address + "set?token=" + configs.at("proxy.token")).openConnection();
                log.info("Dynamic proxy response : " + conn.getResponseMessage());
            }
            catch (IOException e)
            {
                log.error("Couldn't setup dynamic proxy", e);
            }
        }

        log.info("Starting Pronote service...");
        try
        {
            pronote.init();
        }
        catch (IOException e)
        {
            log.fatal("Couldn't init Pronote service, shutting down...", e);
            System.exit(1);
        }

        refresh.start();

        log.info("Configuring HTTP server...");

        Spark.port(configs.at("app.port", int.class));
        Spark.notFound((request, response) -> {
            response.type("application/json");

            JsonObject obj = new JsonObject();
            obj.addProperty("title", APIError.NOT_FOUND);
            obj.addProperty("message", "Can't find route '" + request.uri() + "'");

            return gson.toJson(obj);
        });


        // Disable CORS protection
        Filter corsFilter = (request, response) ->
        {
            response.header("Access-Control-Allow-Origin", "*");

            if (request.requestMethod().toLowerCase().equals("options"))
            {
                response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                response.header("Access-Control-Allow-Headers", "origin, x-csrftoken, content-type, token, accept");

                response.body(Controller.SUCCESS);
            }
        };

        Spark.after(corsFilter);

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

            rep.addProperty("message", (e instanceof RequestException) ? e.getCause().getMessage() : e.getMessage());

            response.body(gson.toJson(rep));

            if (!(e instanceof APIError)&& !(e instanceof RequestException))
            {
                exceptionHandler.handle(new InRequestException(e, request));
            }
            else
            {
                try
                {
                    corsFilter.handle(request, response);
                }
                catch (Exception ignored)
                {
                }
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
