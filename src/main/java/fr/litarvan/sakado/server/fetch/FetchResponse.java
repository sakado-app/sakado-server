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
package fr.litarvan.sakado.server.fetch;

import fr.litarvan.sakado.server.user.data.Averages;
import fr.litarvan.sakado.server.user.data.Homework;
import fr.litarvan.sakado.server.user.data.Subject;
import fr.litarvan.sakado.server.user.data.Week;

public class FetchResponse
{
    public final String error;
    public final String name;
    public final String studentClass;
    public final String avatar;

    public final Week[] timetable;
    public final Homework[] homeworks;
    public final Subject[] marks;
    public final Averages averages;

    public FetchResponse(String error, String name, String studentClass, String avatar, Week[] timetable, Homework[] homeworks, Subject[] marks, Averages averages)
    {
        this.error = error;
        this.name = name;
        this.studentClass = studentClass;
        this.avatar = avatar;
        this.timetable = timetable;
        this.homeworks = homeworks;
        this.marks = marks;
        this.averages = averages;
    }
}
