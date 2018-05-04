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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentClass
{
    private String name;
    private List<User> users;

    public StudentClass(String name)
    {
        this(name, new ArrayList<>());
    }

    public StudentClass(String name, List<User> users)
    {
        this.name = name;
        this.users = users;
    }

    public String getName()
    {
        return name;
    }

    public List<User> getUsers()
    {
        return users;
    }

    public void setAdmin(User user)
    {
        User admin = getAdmin();

        if (admin != null)
        {
            admin.setAdmin(false);
        }

        user.setAdmin(true);
    }

    public User getAdmin()
    {
        for (User user : users)
        {
            if (user.isAdmin())
            {
                return user;
            }
        }

        return null;
    }

    public void addRepresentative(User user)
    {
        user.setRepresentative(true);
    }

    public void removeRepresentative(User user)
    {
        user.setRepresentative(false);
    }

    public User[] getRepresentative()
    {
        return users.stream().filter(User::isRepresentative).toArray(User[]::new);
    }

    public void add(User user)
    {
        this.users.add(user);
    }

    public User getUser(String username)
    {
        for (User user : this.users)
        {
            if (Objects.equals(user.getUsername(), username))
            {
                return user;
            }
        }

        return null;
    }
}
