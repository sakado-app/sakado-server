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
package fr.litarvan.sakado.server.refresh;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.litarvan.sakado.server.data.Homework;
import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.push.PushService;
import fr.litarvan.sakado.server.push.PushType;
import fr.litarvan.sakado.server.util.CalendarUtils;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LongHomeworkTask extends BaseRefreshTask
{
    private static final Logger log = LogManager.getLogger("LongHomeworkTask");

    @Inject
    private PushService push;

    @Override
    public void refresh(User user)
    {
        if (user.getHomeworks() == null)
        {
            return;
        }

        List<Homework> longHomeworks = new ArrayList<>();
        Calendar current = CalendarUtils.create();

        for (Homework homework : user.getHomeworks())
        {
            if (!user.studentClass().getLongHomeworks().contains(homework.getId()))
            {
                continue;
            }

            Calendar date = homework.getUntilAsCalendar();

            if (CalendarUtils.isSameDay(date, current))
            {
                continue;
            }

            date.add(Calendar.HOUR_OF_DAY, -6); // 18h, the last day before

            if (current.after(date))
            {
                longHomeworks.add(homework);
            }
        }

        removeSeen(user, longHomeworks);

        if (longHomeworks.size() == 0)
        {
            return;
        }

        String title;
        String message;

        if (longHomeworks.size() > 1)
        {
            title = longHomeworks.size() + " longs devoirs pour demain";
            message = "Cliquer pour voir";
        }
        else
        {
            title = "Long devoir pour demain";
            message = longHomeworks.get(0).getSubject() + " - " + longHomeworks.get(0).getContent();
        }

        try
        {
            push.send(user, PushType.HOMEWORK, title, message);
        }
        catch (Exception e)
        {
            log.error("Couldn't send away push notification", e);
        }
    }
}