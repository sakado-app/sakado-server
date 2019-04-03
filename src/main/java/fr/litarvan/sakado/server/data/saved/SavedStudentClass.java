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
package fr.litarvan.sakado.server.data.saved;

import fr.litarvan.sakado.server.data.Reminder;

public class SavedStudentClass
{
    private String name;
    private String admin;
    private SavedUser[] members;
    private Reminder[] reminders;
    private String[] longHomeworks;
    private String[] representatives;

    public SavedStudentClass(String name, String admin, SavedUser[] members, Reminder[] reminders, String[] longHomeworks, String[] representatives)
    {
        this.name = name;
        this.admin = admin;
        this.members = members;
        this.reminders = reminders;
        this.longHomeworks = longHomeworks;
        this.representatives = representatives;
    }

    public String getName()
    {
        return name;
    }

    public String getAdmin()
    {
        return admin;
    }

    public SavedUser[] getMembers()
    {
        return members;
    }

    public Reminder[] getReminders()
    {
        return reminders;
    }

    public String[] getLongHomeworks()
    {
        return longHomeworks;
    }

    public String[] getRepresentatives()
    {
        return representatives;
    }
}
