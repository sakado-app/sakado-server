package fr.litarvan.sakado.server.http.controller;

import com.google.gson.JsonObject;
import fr.litarvan.sakado.server.SakadoServer;
import fr.litarvan.sakado.server.http.Controller;
import spark.Request;
import spark.Response;

public class VersionController extends Controller
{
    public Object version(Request request, Response response)
    {
        JsonObject object = new JsonObject();
        object.addProperty("version", SakadoServer.VERSION);

        return json(object, response);
    }
}
