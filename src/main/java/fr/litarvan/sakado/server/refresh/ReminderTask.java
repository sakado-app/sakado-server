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

import fr.litarvan.sakado.server.data.Reminder;
import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.push.PushService;
import fr.litarvan.sakado.server.push.PushType;
import fr.litarvan.sakado.server.util.CalendarUtils;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReminderTask extends BaseRefreshTask
{
    private static final Logger log = LogManager.getLogger("ReminderTask");

    @Inject
    private PushService push;

    @Override
    public void refresh(User user)
    {
        if (CalendarUtils.create().get(Calendar.HOUR_OF_DAY) < 19)
        {
            return;
        }

        List<Reminder> reminders = new ArrayList<>();

        reminders.addAll(user.getReminders());
        reminders.addAll(user.studentClass().getReminders());

        reminders.removeIf(reminder -> !CalendarUtils.isTomorrow(CalendarUtils.fromTimestamp(reminder.getTime())));

        removeSeen(user, reminders);

        if (reminders.size() == 0)
        {
            return;
        }

        String title;
        String message;

        if (reminders.size() > 1)
        {
            title = "Multiples rappels pour demain";
            message = "Cliquer pour voir";
        }
        else
        {
            Reminder reminder = reminders.get(0);

            title = "Rappel pour demain";
            message = reminder.getTitle() + " - " + reminder.getContent();
        }

        try
        {
            push.send(user, PushType.REMINDER, title, message);
        }
        catch (Exception e)
        {
            log.error("Couldn't send reminder push notification", e);
        }
    }
}
