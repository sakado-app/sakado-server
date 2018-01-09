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

import fr.litarvan.sakado.server.util.CalendarUtils;
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
            String from = "Du " + cal.get(Calendar.DAY_OF_MONTH) + " " + CalendarUtils.parse(cal, Calendar.MONTH);

            cal.add(Calendar.DAY_OF_MONTH, 5);
            from += " au " + cal.get(Calendar.DAY_OF_MONTH) + " " + CalendarUtils.parse(cal, Calendar.MONTH);

            JsonObject weekResult = new JsonObject();
            weekResult.addProperty("from", from);
            weekResult.add("content", gson.toJsonTree(away));

            result[i] = weekResult;
        }

        return json(result, response);
    }

    public Object nextCours(Request request, Response response) throws Exception
    {
        User user = requireLogged(request);

        Calendar current = Calendar.getInstance();
        current.add(Calendar.MINUTE, -30);

        Cours next = null;

        for (Cours cours : user.getEDT()[0].getContent())
        {
            if (cours.getDate().after(current))
            {
                next = cours;
                break;
            }
        }

        JsonObject rep = new JsonObject();

        if (next == null)
        {
            rep.addProperty("present", false);
            return json(rep, response);
        }

        rep.addProperty("present", true);
        rep.addProperty("subject", next.getName());
        rep.addProperty("prof", next.getProf());
        rep.addProperty("salle", next.getSalle());

        String date = CalendarUtils.parse(next.getDate(), Calendar.DAY_OF_WEEK, Calendar.DAY_OF_MONTH, Calendar.MONTH) + " - ";
        int start = next.getDate().get(Calendar.HOUR_OF_DAY);

        date += start + "h-" + (start + next.getLength()) + "h";
        rep.addProperty("date",  date);

        return json(rep, response);
    }

    public Object notes(Request request, Response response) throws APIError
    {
        User user = requireLogged(request);

        JsonObject rep = new JsonObject();
        rep.add("lastNotes", gson.toJsonTree(user.getLastNotes()));
        rep.add("moyennes", gson.toJsonTree(user.getMoyennes()));

        return json(rep, response);
    }

    public Object links(Request request, Response response) throws APIError
    {
        return json(links.all(), response);
    }
}
