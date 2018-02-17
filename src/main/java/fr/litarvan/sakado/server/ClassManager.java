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

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ClassManager
{
    private List<StudentClass> classes;

    public ClassManager()
    {
        this.classes = new ArrayList<>();
    }

    public void add(StudentClass studentClass)
    {
        this.classes.add(studentClass);
    }

    public StudentClass get(String pronoteLink, String name)
    {
        for (StudentClass studentClass : classes)
        {
            if (studentClass.getPronoteUrl().equalsIgnoreCase(pronoteLink) && studentClass.getName().equalsIgnoreCase(name))
            {
                return studentClass;
            }
        }

        return null;
    }

    public StudentClass of(User user)
    {
        for (StudentClass studentClass : classes)
        {
            if (studentClass.getMembers().contains(user.getUsername()) && studentClass.getPronoteUrl().equalsIgnoreCase(user.getPronoteUrl()))
            {
                return studentClass;
            }
        }

        return null;
    }

    public boolean exists(User user)
    {
        return of(user) != null;
    }

    public StudentClass[] getClasses()
    {
        return classes.toArray(new StudentClass[classes.size()]);
    }
}
