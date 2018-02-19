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
package fr.litarvan.sakado.server;

import fr.litarvan.sakado.server.pronote.User;

import java.util.ArrayList;

public class StudentClass
{
    private String pronoteUrl;
    private String name;
    private ArrayList<String> members;
    private transient ArrayList<User> loggedUsers;

    private String admin;

    private ArrayList<String> longHomeworks;
    private ArrayList<String> representatives;

    public StudentClass(String pronoteUrl, String name, String admin)
    {
        this(pronoteUrl, name, admin, new ArrayList<>());
    }

    public StudentClass(String pronoteUrl, String name, String admin, ArrayList<String> members)
    {
        this.pronoteUrl = pronoteUrl;
        this.name = name;
        this.members = members;
        this.loggedUsers = new ArrayList<>();

        this.admin = admin;

        this.longHomeworks = new ArrayList<>();
        this.representatives = new ArrayList<>();
    }

    public void add(User user)
    {
        if (!this.members.contains(user.getUsername()))
        {
            this.members.add(user.getUsername());
        }

        this.loggedUsers.add(user);
    }

    public void addRepresentative(User user)
    {
        this.representatives.add(user.getUsername());
    }

    public void setLongHomework(String homework, boolean isLong)
    {
        if (isLong)
        {
            this.longHomeworks.add(homework);
        }
        else
        {
            this.longHomeworks.remove(homework);
        }
    }

    public String getPronoteUrl()
    {
        return pronoteUrl;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<String> getMembers()
    {
        return members;
    }

    public ArrayList<User> getLoggedUsers()
    {
        return loggedUsers;
    }

    public String getAdmin()
    {
        return admin;
    }

    public ArrayList<String> getLongHomeworks()
    {
        return longHomeworks;
    }

    public ArrayList<String> getRepresentatives()
    {
        return representatives;
    }
}
