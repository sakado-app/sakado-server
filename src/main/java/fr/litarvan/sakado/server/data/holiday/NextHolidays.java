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

public class NextHolidays
{
    private DayHoliday day;
    private long untilDay;
    private PeriodHoliday period;
    private long untilPeriod;

    public NextHolidays()
    {
    }

    public NextHolidays(DayHoliday day, long untilDay, PeriodHoliday period, long untilPeriod)
    {
        this.day = day;
        this.untilDay = untilDay;
        this.period = period;
        this.untilPeriod = untilPeriod;
    }

    public DayHoliday getDay()
    {
        return day;
    }

    public long getUntilDay()
    {
        return untilDay;
    }

    public PeriodHoliday getPeriod()
    {
        return period;
    }

    public long getUntilPeriod()
    {
        return untilPeriod;
    }
}
