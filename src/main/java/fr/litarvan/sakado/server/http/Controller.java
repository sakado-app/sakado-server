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
package fr.litarvan.sakado.server.http;

import com.google.gson.Gson;
import fr.litarvan.sakado.server.http.error.APIError;
import fr.litarvan.sakado.server.data.UserManager;
import fr.litarvan.sakado.server.data.User;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

public class Controller
{
    public static final String SUCCESS = "{\n    \"success\": true\n}";
    public static final String FAILURE = "{\n    \"success\": false\n}";

    @Inject
    protected Gson gson;

    @Inject
    private UserManager userManager;

    protected String json(Object data, Response response)
    {
        response.type("application/json; charset=utf-8");
        return data instanceof String ? (String) data : gson.toJson(data);
    }

    protected String require(Request request, String parameter) throws APIError
    {
        String value = request.params(parameter);

        if (value == null)
        {
            value = request.queryParams(parameter);
        }

        if (value == null)
        {
            throw new APIError(APIError.MISSING_PARAMETER, "Missing parameter '" + parameter + "'");
        }

        return value;
    }


    protected User requireLogged(Request request) throws APIError
    {
        User user = userManager.getByToken(request.headers("Token"));

        if (user == null)
        {
            throw new APIError(APIError.UNAUTHORIZED, "You can't do that without being logged");
        }

        return user;
    }

    public String success(Response response)
    {
        return json(SUCCESS, response);
    }

    public String failure(Response response)
    {
        return json(FAILURE, response);
    }

    public String result(Response response, boolean result)
    {
        return result ? success(response) : failure(response);
    }
}