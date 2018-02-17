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
package fr.litarvan.sakado.server.pronote.network;

import fr.litarvan.sakado.server.pronote.Homework;
import fr.litarvan.sakado.server.pronote.Note;
import fr.litarvan.sakado.server.pronote.User.Moyennes;
import fr.litarvan.sakado.server.pronote.Week;

public class FetchResponse
{
    private String classe;
    private String name;
    private String avatar;

    private Week[] edt;

    private Note[] lastNotes;
    private Moyennes moyennes;

    private Homework[] homeworks;

    public FetchResponse()
    {
    }

    public FetchResponse(String classe, String name, String avatar, Week[] edt, Note[] lastNotes, Moyennes moyennes, Homework[] homeworks)
    {
        this.classe = classe;
        this.name = name;
        this.avatar = avatar;
        this.edt = edt;
        this.lastNotes = lastNotes;
        this.moyennes = moyennes;
        this.homeworks = homeworks;
    }

    public String getClasse()
    {
        return classe;
    }

    public String getName()
    {
        return name;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public Week[] getEdt()
    {
        return edt;
    }

    public Note[] getLastNotes()
    {
        return lastNotes;
    }

    public Moyennes getMoyennes()
    {
        return moyennes;
    }

    public Homework[] getHomeworks()
    {
        return homeworks;
    }
}
