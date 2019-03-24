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

import fr.litarvan.sakado.server.data.Homework;
import fr.litarvan.sakado.server.data.Mark;
import fr.litarvan.sakado.server.data.SubjectMarks;
import fr.litarvan.sakado.server.data.Averages;
import fr.litarvan.sakado.server.data.Week;

public class FetchResponse extends Response
{
    private String studentClass;
    private String name;
    private String avatar;

    private Week[] timetable;

    private String[][] menu;

    private SubjectMarks[] marks;
    private Averages averages;

    private Homework[] homeworks;

    public FetchResponse()
    {
    }

    public FetchResponse(String error, String studentClass, String name, String avatar, Week[] timetable, String[][] menu, SubjectMarks[] marks, Averages averages, Homework[] homeworks)
    {
        this.error = error;
        this.studentClass = studentClass;
        this.name = name;
        this.avatar = avatar;
        this.timetable = timetable;
        this.menu = menu;
        this.marks = marks;
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

    public String[][] getMenu()
    {
        return menu;
    }

    public SubjectMarks[] getMarks()
    {
        return marks;
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
