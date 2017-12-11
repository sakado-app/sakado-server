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
package fr.litarvan.sakado.server.http.error;

import com.google.gson.Gson;
import fr.litarvan.sakado.server.Main;
import spark.Response;

public class APIError extends Exception
{
    // Auth
    public static final String INVALID_CREDENTIALS = "Invalid credentials";

    // HTTP
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String MISSING_PARAMETER = "Missing parameter";

    private static Gson gson = Main.injector().getInstance(Gson.class);

    public static final String NOT_FOUND = "Not Found";

    private String title;
    private String message;

    public APIError(String title, String message)
    {
        super(message);

        this.title = title;
        this.message = message;
    }

    public String response(Response response)
    {
        String rep = gson.toJson(this);

        response.type("application/json");
        response.body(rep);

        return rep;
    }

    public String getTitle()
    {
        return title;
    }

    public String getMessage()
    {
        return message;
    }
}