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
package fr.litarvan.sakado.server.pronote;

import fr.litarvan.sakado.server.Classe;
import fr.litarvan.sakado.server.ClasseManager;
import fr.litarvan.sakado.server.pronote.network.RequestException;
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
    public static final long RATE = /*5 * 60 * */1000;

    private static final Logger log = LogManager.getLogger("RefreshService");

    @Inject
    private ClasseManager classes;

    @Inject
    private PushService push;

    @Inject
    private Pronote pronote;

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
                for (Classe classe : classes.getClasses())
                {
                    classe.getLoggedUsers().forEach(RefreshService.this::refresh);
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
                pronote.remove(user);

                return;
            }

            log.error("Unknown error while refreshing data from Pronote, ignoring", e);
        }

        this.checkNewAway(user);
        this.checkNewNote(user);
    }

    protected void checkNewAway(User user)
    {
        List<Cours> away = new ArrayList<>();
        for (Week week : user.getEDT())
        {
            for (Cours cours : week.getContent())
            {
                if (cours.isAway())
                {
                    away.add(cours);
                }
            }
        }

        away.removeIf(c -> {
            String id = getID(user, c);

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

            Cours cours = away.get(0);
            Calendar day = cours.getFrom();

            int start = day.get(Calendar.HOUR_OF_DAY);

            message = cours.getProf();
            message += " : " + CalendarUtils.parse(day, Calendar.DAY_OF_WEEK, Calendar.DAY_OF_MONTH, Calendar.MONTH);
            message += " - " + start + "h-" +  CalendarUtils.parse(cours.getTo(), Calendar.HOUR_OF_DAY) + "h";
        }

        try
        {
            log.info("Sending push notification '" + title + "' : '" + message + "' to '" + user.getUsername() + "'");
            push.send(user, PushType.AWAY, title, message);
        }
        catch (Exception e)
        {
            log.error("Couldn't send away push notification", e);
        }
    }

    protected void checkNewNote(User user)
    {
        Calendar max = CalendarUtils.create();
        max.add(Calendar.DAY_OF_MONTH, -2);

        List<Note> notes = new ArrayList<>();

        for (Note note : user.getLastNotes())
        {
            if (note.getTime().after(max))
            {
                notes.add(note);
            }
        }

        notes.removeIf(n -> {
            String id = getID(user, n);

            if (!seen.contains(id))
            {
                seen.add(id);
                return false;
            }

            return true;
        });

        if (notes.size() == 0)
        {
            return;
        }

        String title = "Sakado - ";
        String message;

        if (notes.size() > 1)
        {
            title += "Nouvelles notes";
            message = "Cliquer pour voir";
        }
        else
        {
            title += "Nouvelle note";
            message = notes.get(0).getSubject() + " - " + notes.get(0).getNote();
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

    protected String getID(User user, Note note)
    {
        return user.getUsername() + "-" + note.getNote() + "-" + note.getSubject() + "-" + note.getTime().get(Calendar.DAY_OF_MONTH) + "-" + note.getTime().get(Calendar.MONTH);
    }

    protected String getID(User user, Cours cours)
    {
        return user.getUsername() + "-" + cours.getFrom().getTimeInMillis();
    }
}
