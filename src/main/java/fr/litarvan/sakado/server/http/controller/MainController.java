package fr.litarvan.sakado.server.http.controller;

import com.google.gson.JsonObject;
import fr.litarvan.sakado.server.http.Controller;
import fr.litarvan.sakado.server.http.error.APIError;
import fr.litarvan.sakado.server.pronote.Cours;
import fr.litarvan.sakado.server.pronote.PronoteLinks;
import fr.litarvan.sakado.server.pronote.User;
import fr.litarvan.sakado.server.pronote.Week;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
                if (cours.isAway())
                {
                    away.add(cours);
                }
            }

            Calendar cal = week.getFrom();
            String from = "Du " + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRANCE);

            cal.set(Calendar.DAY_OF_MONTH, 5);
            from += " au " + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRANCE);

            JsonObject weekResult = new JsonObject();
            weekResult.addProperty("from", from);
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
