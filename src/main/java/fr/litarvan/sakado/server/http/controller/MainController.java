package fr.litarvan.sakado.server.http.controller;

import com.google.gson.JsonObject;
import fr.litarvan.sakado.server.http.Controller;
import fr.litarvan.sakado.server.http.error.APIError;
import fr.litarvan.sakado.server.pronote.Cours;
import fr.litarvan.sakado.server.pronote.PronoteLinks;
import fr.litarvan.sakado.server.pronote.User;
import fr.litarvan.sakado.server.pronote.Week;
import java.util.ArrayList;
import java.util.List;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

public class MainController extends Controller
{
    @Inject
    private PronoteLinks links;

    public Object away(Request request, Response response) throws APIError
    {
        User user = requireLogged(request);

        Week[] edt = user.getEDT();
        JsonObject[] result = new JsonObject[edt.length];

        for (int i = 0; i < edt.length; i++)
        {
            Week week = edt[i];
            List<Cours> away = new ArrayList<>();

            for (Cours cours : week.getContent())
            {
                if ("Prof. absent".equalsIgnoreCase(cours.getInfo()))
                {
                    away.add(cours);
                }
            }

            JsonObject weekResult = new JsonObject();
            weekResult.addProperty("from", week.getFrom());
            weekResult.add("content", gson.toJsonTree(away));

            result[i] = weekResult;
        }

        return json(result, response);
    }

    public Object links(Request request, Response response) throws APIError
    {
        return json(links.all(), response);
    }
}
