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
package fr.litarvan.sakado.server.pronote.network;

import com.google.gson.JsonObject;
import fr.litarvan.sakado.server.pronote.RequestException;
import org.apache.commons.lang3.StringUtils;

public class Response
{
    private int id;

    private Status status;
    private String error;
    private JsonObject result;

    Response(int id, Status status, String error, JsonObject result)
    {
        this.id = id;
        this.status = status;
        this.error = error;
        this.result = result;
    }

    int getId()
    {
        return id;
    }

    public void doThrow() throws RequestException
    {
        if (status == Status.ERROR || status == Status.INTERNAL_ERROR)
        {
            throw new RequestException(StringUtils.capitalize(status.name().toLowerCase()).replace(" ", "") + " : " + error);
        }
    }

    public Status getStatus()
    {
        return status;
    }

    public String getError()
    {
        return error;
    }

    public JsonObject getResult()
    {
        return result;
    }
}
