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
package fr.litarvan.sakado.server.data.saved;

import fr.litarvan.sakado.server.data.Reminder;

public class SavedUser
{
    private String token;
    private String establishment;
    private String method;
    private String username;
    private String password;
    private String deviceToken;
    private Reminder[] reminders;
    private long lastLogin;

    public SavedUser(String token, String establishment, String method, String username, String password, String deviceToken, Reminder[] reminders, long lastLogin)
    {
        this.token = token;
        this.establishment = establishment;
        this.method = method;
        this.username = username;
        this.password = password;
        this.deviceToken = deviceToken;
        this.reminders = reminders;
        this.lastLogin = lastLogin;
    }

    public String getToken()
    {
        return token;
    }

    public String getEstablishment()
    {
        return establishment;
    }

	public String getMethod()
	{
		return method;
	}

	public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getDeviceToken()
    {
        return deviceToken;
    }

    public Reminder[] getReminders()
    {
        return reminders;
    }

    public long getLastLogin()
    {
        return lastLogin;
    }
}
