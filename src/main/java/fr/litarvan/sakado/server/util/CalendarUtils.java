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
package fr.litarvan.sakado.server.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.*;

public final class CalendarUtils
{
    public static Calendar create()
    {
        return getInstance();
    }

    public static boolean isTomorrow(Calendar calendar)
    {
        Calendar current = create();
        current.add(Calendar.DAY_OF_MONTH, 1);
        current.add(Calendar.HOUR_OF_DAY, -3); // For late people

        return isSameDay(current, calendar);
    }

    public static boolean isSameDay(Calendar a, Calendar b)
    {
        return a.get(DAY_OF_MONTH) == b.get(DAY_OF_MONTH) && a.get(MONTH) == b.get(MONTH) && a.get(YEAR) == b.get(YEAR);
    }

    public static String parse(Calendar calendar, int... fields)
    {
        StringBuilder result = new StringBuilder();

        for (int field : fields)
        {
            String res = calendar.getDisplayName(field, LONG, Locale.FRANCE);

            if (res == null)
            {
                res = String.valueOf(calendar.get(field));

                if (res.length() == 1)
                {
                    res = "0" + res;
                }
            }
            else
            {
                res = StringUtils.capitalize(res);
            }

            result.append(res).append(" ");
        }

        return result.substring(0, result.length() - 1);
    }

    public static Calendar fromTimestamp(long timestamp)
    {
        Calendar calendar = CalendarUtils.create();
        calendar.setTimeInMillis(timestamp);

        return calendar;
    }
}
