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
package fr.litarvan.sakado.server.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.litarvan.paladin.Session;
import fr.litarvan.sakado.server.user.data.*;

@JsonIgnoreProperties({"username", "password", "studentClass", "establishment", "data", "session", "logged", "timetable", "homeworks", "marks", "averages"})
public class User
{
    private String username;
    private String password;

    private String fullName;
    private StudentClass studentClass;
    private String avatar;

    private boolean admin;
    private boolean representative;

    private String deviceToken;

    private Establishment establishment;

    private UserData data;

    private Session session;

    public User(String username, String password, String fullName, StudentClass studentClass, String avatar, Establishment establishment, UserData data)
    {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.studentClass = studentClass;
        this.avatar = avatar;
        this.admin = false;
        this.representative = false;
        this.establishment = establishment;
        this.data = data;
    }

    public void updateData(UserData data)
    {
        this.data = data;
    }

    public String getToken()
    {
        return session.getToken();
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getFullName()
    {
        return fullName;
    }

    public StudentClass getStudentClass()
    {
        return studentClass;
    }

    public String getStudentClassName()
    {
        return getStudentClass().getName();
    }

    public String getAvatar()
    {
        return avatar;
    }

    public boolean isAdmin()
    {
        return admin;
    }

    public void setAdmin(boolean admin)
    {
        this.admin = admin;
    }

    public boolean isRepresentative()
    {
        return representative;
    }

    public void setRepresentative(boolean representative)
    {
        this.representative = representative;
    }

    public String getDeviceToken()
    {
        return deviceToken;
    }

    public Establishment getEstablishment()
    {
        return establishment;
    }

    public void setDeviceToken(String deviceToken)
    {
        this.deviceToken = deviceToken;
    }

    public Week[] getTimetable()
    {
        return data.getTimetable();
    }

    public Homework[] getHomeworks()
    {
        return data.getHomeworks();
    }

    public Subject[] getMarks()
    {
        return data.getMarks();
    }

    public Averages getAverages()
    {
        return data.getAverages();
    }

    public Session getSession()
    {
        if (session != null && session.isExpired())
        {
            return null;
        }

        return session;
    }

    public void setSession(Session session)
    {
        this.session = session;
    }

    public boolean isLogged()
    {
        return getSession() != null;
    }

    public void logout()
    {
        this.session = null;
    }
}
