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

import fr.litarvan.sakado.server.util.CalendarUtils;

import java.util.Calendar;

public class Week
{
    private long from;
    private long to;
    private Lesson[] content;

    public Week()
    {
    }

    public Week(long from, long to, Lesson[] content)
    {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public long getFrom()
    {
        return from;
    }

    public Calendar getFromAsCalendar()
    {
        return CalendarUtils.fromTimestamp(from);
    }

    public long getTo()
    {
        return to;
    }

    public Calendar getToAsCalendar()
    {
        return CalendarUtils.fromTimestamp(to);
    }

    public Lesson[] getContent()
    {
        return content;
    }

    public Week cloneWith(Lesson[] content)
    {
        return new Week(this.from, this.to, content);
    }

    public String getId()
    {
        return "W" + this.getFrom() + "" + this.getTo();
    }
}
