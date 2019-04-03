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

import java.util.Calendar;

import fr.litarvan.sakado.server.util.CalendarUtils;

public class Reminder implements Identifiable
{
    private String title;
    private String content;
    private String author;
    private long time;

    public Reminder()
    {
    }

    public Reminder(String title, String content, String author, long time)
    {
        this.title = title;
        this.content = content;
        this.author = author;
        this.time = time;
    }

    public String getTitle()
    {
        return title;
    }

    public String getContent()
    {
        return content;
    }

    public String getAuthor()
    {
        return author;
    }

    public long getTime()
    {
        return time;
    }

    public Calendar getTimeAsCalendar()
    {
        return CalendarUtils.fromTimestamp(getTime());
    }

    @Override
    public String getId()
    {
        return "R" + this.title + ":" + this.content + ":" + getTime();
    }
}
