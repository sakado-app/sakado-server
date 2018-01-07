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
    @Inject
    private ClasseManager classes;

    @Inject
    private PushService push;

    public static final long RATE = 5 * 60 * 1000;

    private static final Logger log = LogManager.getLogger("RefreshService");

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
        user.update();

        // Check for away teachers
        StringBuilder result = new StringBuilder();
        for (Week week : user.getEDT())
        {
            for (Cours cours : week.getContent())
            {
                if (cours.isAway())
                {
                    Calendar day = cours.getDate();

                    int start = day.get(Calendar.HOUR_OF_DAY);

                    String message = cours.getProf();
                    message += " : " + CalendarUtils.parse(day, Calendar.DAY_OF_WEEK, Calendar.DAY_OF_MONTH, Calendar.MONTH);
                    message += " - " + start + "h-" + (start + cours.getLength()) + "h";

                    result.append(message).append(" // ");
                }
            }
        }

        if (result.length() > 0)
        {
            String title = "Sakado - ";

            if (result.indexOf(" // ") > 1)
            {
                title += "Plusieurs profs. absents";
            }
            else
            {
                title += "Prof. absent";
            }

            try
            {
                push.send(user, PushType.AWAY, title, result.substring(0, result.length() - 4));
            }
            catch (Exception e)
            {
                log.error("Couldn't send away push notification", e);
            }
        }
    }
}
