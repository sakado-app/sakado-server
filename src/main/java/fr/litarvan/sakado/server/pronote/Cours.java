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

import java.util.Calendar;

public class Cours
{
    private String info;

    private String name;
    private String prof;
    private String salle;

    private int length;

    private int weekday;
    private int day;
    private int hour;

    public Cours()
    {
    }

    public Cours(String info, String name, String prof, String salle, int length, int weekday, int day, int hour)
    {
        this.info = info;
        this.name = name;
        this.prof = prof;
        this.salle = salle;
        this.length = length;
        this.weekday = weekday;
        this.day = day;
        this.hour = hour;
    }

    public String getInfo()
    {
        return info;
    }

    public String getName()
    {
        return name;
    }

    public String getProf()
    {
        return prof;
    }

    public String getSalle()
    {
        return salle;
    }

    public int getLength()
    {
        return length;
    }

    public int getWeekday()
    {
        return weekday;
    }

    public int getDay()
    {
        return day;
    }

    public int getHour()
    {
        return hour;
    }

    public Calendar getDate()
    {
        Calendar calendar = Calendar.getInstance();
        if (getDay() < calendar.get(Calendar.DAY_OF_MONTH))
        {
            calendar.add(Calendar.MONTH, 1);
        }

        calendar.set(Calendar.DAY_OF_MONTH, getDay());
        calendar.set(Calendar.HOUR_OF_DAY, getHour() + 8);

        return calendar;
    }

    public boolean isAway()
    {
        return "Prof. absent".equalsIgnoreCase(this.getInfo());
    }
}
