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
package fr.litarvan.sakado.server.data.network;

public class DataRequest
{
    private String type;
    private String username;
    private String password;
    private String url;
    private String cas;

    public DataRequest(String type, String username, String password, String url, String cas)
    {
        this.type = type;
        this.username = username;
        this.password = password;
        this.url = url;
        this.cas = cas;
    }

    public String getType() {
        return type;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getUrl()
    {
        return url;
    }

    public String getCas()
    {
        return cas;
    }
}
