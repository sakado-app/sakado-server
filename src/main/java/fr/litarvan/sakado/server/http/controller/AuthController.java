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
package fr.litarvan.sakado.server.http.controller;

import com.google.gson.JsonObject;
import fr.litarvan.sakado.server.http.Controller;
import fr.litarvan.sakado.server.http.error.APIError;
import fr.litarvan.sakado.server.pronote.LoginException;
import fr.litarvan.sakado.server.pronote.Pronote;
import fr.litarvan.sakado.server.pronote.User;
import fr.litarvan.sakado.server.pronote.network.RequestException;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.io.IOException;

public class AuthController extends Controller
{
    @Inject
    private Pronote pronote;

    public Object login(Request request, Response response) throws IOException, RequestException, APIError
    {
        String username = require(request, "username");
        String password = require(request, "password");
        String deviceToken = require(request, "deviceToken");

        String link = require(request, "link");

        User user;

        try
        {
            user = pronote.login(link, username, password, deviceToken);
        }
        catch (LoginException e)
        {
            throw new APIError(APIError.INVALID_CREDENTIALS, e.getMessage());
        }

        return json(apply(user), response);
    }

    public Object validate(Request request, Response response) throws APIError
    {
        return json(apply(this.requireLogged(request)), response);
    }

    public Object logout(Request request, Response response) throws IOException, RequestException, APIError
    {
        this.pronote.logout(requireLogged(request));
        return success(response);
    }

    protected JsonObject apply(User user)
    {
        JsonObject rep = new JsonObject();
        rep.addProperty("success", "true");
        rep.addProperty("token", user.getToken());

        rep.addProperty("name", user.getName());
        rep.addProperty("classe", user.getClasse());
        rep.addProperty("avatar", user.getAvatar());

        return rep;
    }
}
