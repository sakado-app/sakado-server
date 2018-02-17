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

public class Cours
{
    private String name;
    private String prof;
    private String salle;

    private int from;
    private int to;

    private boolean away;

    public Cours()
    {
    }

    public Cours(String name, String prof, String salle, int from, int to, boolean away)
    {
        this.name = name;
        this.prof = prof;
        this.salle = salle;
        this.from = from;
        this.to = to;
        this.away = away;
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

    public Calendar getFrom()
    {
        return CalendarUtils.fromTimestamp(from);
    }

    public Calendar getTo()
    {
        return CalendarUtils.fromTimestamp(to);
    }

    public boolean isAway()
    {
        return away;
    }
}
