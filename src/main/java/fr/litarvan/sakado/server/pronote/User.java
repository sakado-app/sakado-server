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
package fr.litarvan.sakado.server.pronote;

import fr.litarvan.sakado.server.pronote.network.RequestException;
import fr.litarvan.sakado.server.pronote.network.body.TokenBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class User
{
    private static final Logger log = LogManager.getLogger("Pronote");

    private String username;
    private String token;

    private Pronote pronote;
    private Cours[] edt;
    private Homework[] homeworks;

    protected User(Pronote pronote, String username, String token)
    {
        this.pronote = pronote;
        this.username = username;
        this.token = token;
    }

    static User open(Pronote pronote, String username) throws IOException, RequestException
    {
        TokenBody response = pronote.getClient().push("open", TokenBody.class);
        return new User(pronote, username, response.getToken());
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
        this.edt = pronote.getClient().push("edt", new TokenBody(token), Cours[].class);
        this.homeworks = pronote.getClient().push("homeworks", new TokenBody(token), Homework[].class);
    }

    public boolean isLogged()
    {
        return edt != null;
    }

    public String getUsername()
    {
        return username;
    }

    String getToken()
    {
        return token;
    }

    public Cours[] getEDT()
    {
        return edt;
    }

    public Homework[] getHomeworks()
    {
        return homeworks;
    }
}
