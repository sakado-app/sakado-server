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
package fr.litarvan.sakado.server.pronote;

import fr.litarvan.sakado.server.pronote.network.RequestException;
import fr.litarvan.sakado.server.pronote.network.body.NotesResponse;
import fr.litarvan.sakado.server.pronote.network.body.TokenBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class User
{
    private static final Logger log = LogManager.getLogger("Pronote");

    private String pronoteUrl;
    private String username;
    private String name;
    private String classe;
    private String avatar;
    private String token;
    private String deviceToken;

    private Pronote pronote;
    private Week[] edt;
    private Homework[] homeworks;
    private Note[] lastNotes;
    private String[] moyennes;

    protected User(Pronote pronote, String pronoteUrl, String username, String token, String deviceToken)
    {
        this.pronote = pronote;
        this.pronoteUrl = pronoteUrl;
        this.username = username;
        this.token = token;
        this.deviceToken = deviceToken;
    }

    static User open(Pronote pronote, String pronoteUrl, String username, String devicetoken) throws IOException, RequestException
    {
        TokenBody response = pronote.getClient().push("open", TokenBody.class);
        return new User(pronote, pronoteUrl, username, response.getToken(), devicetoken);
    }

    public void update()
    {
        try
        {
            tryToUpdate();
        }
        catch (IOException | RequestException e)
        {
            log.error("Couldn't query user data from Pronote, ignoring", e);
        }
    }

    public void tryToUpdate() throws IOException, RequestException
    {
        this.edt = pronote.getClient().push("edt", new TokenBody(token), Week[].class);
        this.homeworks = pronote.getClient().push("homeworks", new TokenBody(token), Homework[].class);

        NotesResponse response = pronote.getClient().push("notes", new TokenBody(token), NotesResponse.class);
        this.lastNotes = response.getLastNotes();
        this.moyennes = response.getMoyennes();
    }

    public boolean isLogged()
    {
        return edt != null;
    }

    public String getPronoteUrl()
    {
        return pronoteUrl;
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

    public String getClasse()
    {
        return classe;
    }

    void setClasse(String classe)
    {
        this.classe = classe;
    }

    public String getAvatar()
    {
        return avatar;
    }

    void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getToken()
    {
        return token;
    }

    public Week[] getEDT()
    {
        return edt;
    }

    public Homework[] getHomeworks()
    {
        return homeworks;
    }

    public Note[] getLastNotes()
    {
        return lastNotes;
    }

    public String[] getMoyennes()
    {
        return moyennes;
    }

    public String getDeviceToken()
    {
        return deviceToken;
    }
}
