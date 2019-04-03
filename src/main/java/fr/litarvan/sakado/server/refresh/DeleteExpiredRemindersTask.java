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

import java.util.Calendar;
import java.util.function.Predicate;

import fr.litarvan.sakado.server.data.Reminder;
import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.util.CalendarUtils;

public class DeleteExpiredRemindersTask extends BaseRefreshTask
{
    @Override
    public void refresh(User user)
    {
        Predicate<Reminder> remover = reminder -> {
            Calendar time = CalendarUtils.fromTimestamp(reminder.getTime());
            time.add(Calendar.DAY_OF_MONTH, 1);

            return time.getTimeInMillis() < Calendar.getInstance().getTimeInMillis();
        };

        user.getReminders().removeIf(remover);
        user.studentClass().getReminders().removeIf(remover);
    }
}
