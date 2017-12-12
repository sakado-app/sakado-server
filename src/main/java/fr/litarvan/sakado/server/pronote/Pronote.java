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

import com.google.gson.JsonObject;
import fr.litarvan.commons.config.ConfigProvider;
import fr.litarvan.sakado.server.pronote.network.NetworkClient;
import fr.litarvan.sakado.server.pronote.network.request.TokenRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class Pronote
{
    @Inject
    private ConfigProvider config;

    private NetworkClient client;
    private List<User> users;

    public Pronote()
    {
        this.users = new ArrayList<>();
    }

    public void init() throws IOException
    {
        client = new NetworkClient(config.at("pronote.server-host"), config.at("pronote.server-port", int.class));
    }

    public User login(String username, String password) throws IOException
    {
        User user = get(username);

        if (user != null)
        {
            if (user.isLogged())
            {
                return user;
            }
        }
        else
        {
            user = User.open(this, username);
        }

        JsonObject params = new JsonObject();
        params.addProperty("username", username);
        params.addProperty("password", password);

        this.users.add(user);

        String token = user.getToken();
        try
        {
            client.push("login", new TokenRequest(token));
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }

        user.tryToUpdate();

        return user;
    }

    public User get(String username)
    {
        for (User user : users)
        {
            if (user.getUsername().equalsIgnoreCase(username))
            {
                return user;
            }
        }

        return null;
    }

    public User getByToken(String token)
    {
        for (User user : users)
        {
            if (user.getToken().equals(token))
            {
                return user;
            }
        }

        return null;
    }

    public NetworkClient getClient()
    {
        return client;
    }
}
