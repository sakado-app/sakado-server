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

import fr.litarvan.sakado.server.data.network.FetchResponse;
import fr.litarvan.sakado.server.data.network.FetchRequest;
import fr.litarvan.sakado.server.data.network.RequestException;

import java.io.IOException;

public class User
{
    private String token;

    private Establishment establishment;

    private String username;
    private String password;

    private String deviceToken;

    private String name;
    private String studentClass;
    private String avatar;

    private DataServer server;

    private Week[] timetable;
    private Homework[] homeworks;
    private SubjectMarks[] marks;
    private Mark[] lastMarks;
    private Averages averages;

    public User(DataServer server, String token, Establishment establishment, String username, String password, String deviceToken)
    {
        this.server = server;
        this.token = token;
        this.establishment = establishment;
        this.username = username;
        this.password = password;
        this.deviceToken = deviceToken;
    }

    public void update() throws IOException, RequestException
    {
        FetchResponse response = server.fetch(new FetchRequest(username, password, establishment.getMethod().getUrl(), establishment.getMethod().getCas()));

        this.name = response.getName();
        this.studentClass = response.getStudentClass();
        this.avatar = response.getAvatar();

        this.timetable = response.getTimetable();
        this.homeworks = response.getHomeworks();
        this.marks = response.getMarks();
        this.lastMarks = response.getLastMarks();
        this.averages = response.getAverages();
    }

    public StudentClass studentClass()
    {
        return getEstablishment().classOf(this);
    }

    public String getToken()
    {
        return token;
    }

    public Establishment getEstablishment()
    {
        return establishment;
    }

    public String getUsername()
    {
        return username;
    }

    public String getName()
    {
        return name;
    }

    void setName(String name)
    {
        this.name = name;
    }

    public String getStudentClass()
    {
        return studentClass;
    }

    void setStudentClass(String studentClass)
    {
        this.studentClass = studentClass;
    }

    public String getAvatar()
    {
        return avatar;
    }

    void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public Week[] getTimetable()
    {
        return timetable;
    }

    public Homework[] getHomeworks()
    {
        return homeworks;
    }

    public SubjectMarks[] getMarks()
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

    public String getDeviceToken()
    {
        return deviceToken;
    }

}
