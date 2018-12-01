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

import java.util.ArrayList;
import java.util.Arrays;

public class StudentClass implements Identifiable
{
    private Establishment establishment;
    private String name;
    private ArrayList<String> members;
    private transient ArrayList<User> loggedUsers;

    private String admin;

    private ArrayList<String> longHomeworks;
    private ArrayList<String> representatives;
    private ArrayList<Reminder> reminders;

    public StudentClass(Establishment establishment, String name, String admin)
    {
        this(establishment, name, admin, new ArrayList<>());
    }

    public StudentClass(Establishment establishment, String name, String admin, ArrayList<String> members)
    {
        this.establishment = establishment;
        this.name = name;
        this.members = members;
        this.loggedUsers = new ArrayList<>();

        this.admin = admin;

        this.longHomeworks = new ArrayList<>();
        this.representatives = new ArrayList<>();
        this.reminders = new ArrayList<>();
    }

    public void add(User user)
    {
        if (!this.members.contains(user.getName()))
        {
            this.members.add(user.getName());
        }

        this.loggedUsers.add(user);
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

    public Establishment getEstablishment()
    {
        return establishment;
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

    public void setAdmin(String admin)
    {
        this.admin = admin;
    }

    public ArrayList<String> getLongHomeworks()
    {
        return longHomeworks;
    }

    public ArrayList<String> getRepresentatives()
    {
        return representatives;
    }

    public ArrayList<Reminder> getReminders()
    {
        return reminders;
    }

    @Override
    public String getId()
    {
        return getEstablishment().getName() + "-" + getName();
    }
}
