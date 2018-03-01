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

public class Lesson implements Identifiable
{
    private String name;
    private String teacher;
    private String room;

    private long from;
    private long to;

    private boolean away;

    public Lesson()
    {
    }

    public Lesson(String name, String teacher, String room, long from, long to, boolean away)
    {
        this.name = name;
        this.teacher = teacher;
        this.room = room;
        this.from = from;
        this.to = to;
        this.away = away;
    }

    public String getName()
    {
        return name;
    }

    public String getTeacher()
    {
        return teacher;
    }

    public String getRoom()
    {
        return room;
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

    public boolean isAway()
    {
        return away;
    }

    @Override
    public String getId()
    {
        return "L" + this.getFrom();
    }
}
