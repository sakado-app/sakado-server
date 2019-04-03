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
package fr.litarvan.sakado.server.data.holiday;

public class PeriodHoliday
{
    private String name;
    private long from;
    private long to;

    public PeriodHoliday()
    {
    }

    public PeriodHoliday(String name, long from, long to)
    {
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public String getName()
    {
        return name;
    }

    public long getFrom()
    {
        return from;
    }

    public void setFrom(long from)
    {
        this.from = from;
    }

    public long getTo()
    {
        return to;
    }

    public void setTo(long to)
    {
        this.to = to;
    }
}
