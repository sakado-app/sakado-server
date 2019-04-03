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

public class Tomorrow
{
    private long tomorrow;
    private Lesson[] timetable;
    private Reminder[] reminders;
    private Homework[] homeworks;

    public Tomorrow(long tomorrow, Lesson[] timetable, Reminder[] reminders, Homework[] homeworks)
    {
        this.tomorrow = tomorrow;
        this.timetable = timetable;
        this.reminders = reminders;
        this.homeworks = homeworks;
    }

    public Tomorrow()
    {
    }

    public long getTomorrow()
    {
        return tomorrow;
    }

    public Lesson[] getTimetable()
    {
        return timetable;
    }

    public Reminder[] getReminders()
    {
        return reminders;
    }

    public Homework[] getHomeworks()
    {
        return homeworks;
    }
}
