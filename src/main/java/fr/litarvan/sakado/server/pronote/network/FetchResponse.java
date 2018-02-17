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
package fr.litarvan.sakado.server.pronote.network;

import fr.litarvan.sakado.server.pronote.Homework;
import fr.litarvan.sakado.server.pronote.Mark;
import fr.litarvan.sakado.server.pronote.User.Averages;
import fr.litarvan.sakado.server.pronote.Week;

public class FetchResponse
{
    private String studentClass;
    private String name;
    private String avatar;

    private Week[] timetable;

    private Mark[] lastMarks;
    private Averages averages;

    private Homework[] homeworks;

    public FetchResponse()
    {
    }

    public FetchResponse(String studentClass, String name, String avatar, Week[] timetable, Mark[] lastMarks, Averages averages, Homework[] homeworks)
    {
        this.studentClass = studentClass;
        this.name = name;
        this.avatar = avatar;
        this.timetable = timetable;
        this.lastMarks = lastMarks;
        this.averages = averages;
        this.homeworks = homeworks;
    }

    public String getStudentClass()
    {
        return studentClass;
    }

    public String getName()
    {
        return name;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public Week[] getTimetable()
    {
        return timetable;
    }

    public Mark[] getLastMarks()
    {
        return lastMarks;
    }

    public Averages getAverages()
    {
        return averages;
    }

    public Homework[] getHomeworks()
    {
        return homeworks;
    }
}
