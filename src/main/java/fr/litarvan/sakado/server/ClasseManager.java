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

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ClasseManager
{
    private List<Classe> classes;

    public ClasseManager()
    {
        this.classes = new ArrayList<>();
    }

    public void add(Classe classe)
    {
        this.classes.add(classe);
    }

    public Classe get(String pronoteLink, String name)
    {
        for (Classe classe : classes)
        {
            if (classe.getPronoteUrl().equalsIgnoreCase(pronoteLink) && classe.getName().equalsIgnoreCase(name))
            {
                return classe;
            }
        }

        return null;
    }

    public Classe of(User user)
    {
        for (Classe classe : classes)
        {
            if (classe.getMembers().contains(user.getUsername()) && classe.getPronoteUrl().equalsIgnoreCase(user.getPronoteUrl()))
            {
                return classe;
            }
        }

        return null;
    }

    public boolean exists(User user)
    {
        return of(user) != null;
    }

    public Classe[] getClasses()
    {
        return classes.toArray(new Classe[classes.size()]);
    }
}
