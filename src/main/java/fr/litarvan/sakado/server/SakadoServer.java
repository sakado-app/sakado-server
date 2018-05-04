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
package fr.litarvan.sakado.server;

import fr.litarvan.paladin.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SakadoServer extends App
{
    private static final Logger log = LoggerFactory.getLogger("SakadoServer");
    public static final String VERSION = "1.0.0";

    @Override
    public void onStart()
    {
        log.info("Starting Sakado server...");
    }

    @Override
    public void onStop()
    {
    }

    @Override
    public String getName()
    {
        return "Sakado Server";
    }

    @Override
    public String getVersion()
    {
        return VERSION;
    }
}
