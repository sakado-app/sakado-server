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
package fr.litarvan.sakado.server.push;

import java.util.ArrayList;
import java.util.List;

public class PushInfo
{
    private String deviceToken;
    private List<String> sent;

    public PushInfo(String deviceToken)
    {
        this.deviceToken = deviceToken;
        this.sent = new ArrayList<>();
    }

    public String getDeviceToken()
    {
        return deviceToken;
    }

    public boolean isToSend(String message)
    {
        return this.deviceToken != null && !this.sent.contains(message);
    }

    public void sent(String message)
    {
        this.sent.add(message);
    }
}
