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

public class Homework implements Identifiable
{
    private String subject;
    private String content;
    private long since;
    private long until;

    public Homework()
    {
    }

    public Homework(String subject, String content, long since, long until)
    {
        this.subject = subject;
        this.content = content;
        this.since = since;
        this.until = until;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getContent()
    {
        return content;
    }

    public long getSince()
    {
        return since;
    }

    public Calendar getSinceAsCalendar()
    {
        return CalendarUtils.fromTimestamp(since);
    }

    public long getUntil()
    {
        return until;
    }

    public Calendar getUntilAsCalendar()
    {
        return CalendarUtils.fromTimestamp(until);
    }

    @Override
    public String getId()
    {
        return "H" + since + "" + until + getSubject().substring(0, 2) + getContent().substring(0, 5);
    }
}
