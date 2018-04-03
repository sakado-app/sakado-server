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
package fr.litarvan.sakado.server.data.network;

import java.util.Map;

import fr.litarvan.sakado.server.data.Homework;
import fr.litarvan.sakado.server.data.Mark;
import fr.litarvan.sakado.server.data.User.Averages;
import fr.litarvan.sakado.server.data.Week;

public class FetchResponse
{
    private String studentClass;
    private String name;
    private String avatar;

    private Week[] timetable;

    private Map<String, Mark[]> marks;
    private Mark[] lastMarks;
    private Averages averages;

    private Homework[] homeworks;

    public FetchResponse()
    {
    }

    public FetchResponse(String studentClass, String name, String avatar, Week[] timetable, Map<String, Mark[]> marks, Mark[] lastMarks, Averages averages, Homework[] homeworks)
    {
        this.studentClass = studentClass;
        this.name = name;
        this.avatar = avatar;
        this.timetable = timetable;
        this.marks = marks;
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

    public Map<String, Mark[]> getMarks()
    {
        return marks;
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
