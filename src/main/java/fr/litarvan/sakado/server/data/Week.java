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
package fr.litarvan.sakado.server.data;

import fr.litarvan.sakado.server.util.CalendarUtils;

import java.util.Calendar;

public class Week implements Identifiable
{
    private long time;
    private Lesson[] content;

    public Week()
    {
    }

    public Week(long time, Lesson[] content)
    {
        this.time = time;
        this.content = content;
    }

    public long getTime()
    {
        return time;
    }

    public Calendar getTimeAsCalendar()
    {
        return CalendarUtils.fromTimestamp(time);
    }

    public Lesson[] getContent()
    {
        return content;
    }

    public Week cloneWith(Lesson[] content)
    {
        return new Week(this.time, content);
    }

    @Override
    public String getId()
    {
        return "W" + this.getTime();
    }
}
