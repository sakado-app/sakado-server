/*
 *  Sakado, an app for school
 *  Copyright (C) 2017 Adrien 'Litarvan' Navratil
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

public class Classe
{
    private String pronoteUrl;
    private String name;
    private ArrayList<String> members;
    private transient ArrayList<User> loggedUsers;

    public Classe(String pronoteUrl, String name)
    {
        this(pronoteUrl, name, new ArrayList<>());
    }

    public Classe(String pronoteUrl, String name, ArrayList<String> members)
    {
        this.pronoteUrl = pronoteUrl;
        this.name = name;
        this.members = members;
        this.loggedUsers = new ArrayList<>();
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
}
