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
package fr.litarvan.sakado.server.push;

public enum PushType
{
    AWAY("#BB0000", "sakado_notify"),
    MARK("#1E6ADD", "sakado_notify"),
    REMINDER("#DD7200", "sakado_notify"),
    HOMEWORK("#41A12E", "sakado_notify"),
    NOTIFY("#E7DA18", "sakado_notify"),
    DISCONNECT("#FF7900", "sakado_notify");

    private String color;
    private String icon;

    PushType(String color, String icon)
    {
        this.color = color;
        this.icon = icon;
    }

    public String getColor()
    {
        return color;
    }

    public String getIcon()
    {
        return icon;
    }
}
