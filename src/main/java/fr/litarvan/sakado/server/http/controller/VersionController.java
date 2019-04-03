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
import fr.litarvan.sakado.server.SakadoServer;
import fr.litarvan.sakado.server.http.Controller;
import spark.Request;
import spark.Response;

public class VersionController extends Controller
{
    public Object version(Request request, Response response)
    {
        JsonObject object = new JsonObject();
        object.addProperty("version", SakadoServer.VERSION);

        return json(object, response);
    }
}
