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
package fr.litarvan.sakado.server.data;

import fr.litarvan.commons.config.ConfigProvider;

import fr.litarvan.sakado.server.data.network.DataServer;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SakadoData
{
    private DataServer[] servers;
    private Establishment[] establishments;

    @Inject
    private ConfigProvider config;

    public void init()
    {
        this.servers = config.at("data.servers", DataServer[].class);
        this.establishments = config.at("data.establishments", Establishment[].class);
    }

    public Establishment getEstablishment(String name)
    {
        for (Establishment establishment : establishments)
        {
            if (establishment.getName().equalsIgnoreCase(name))
            {
                return establishment;
            }
        }

        return null;
    }

    public DataServer getServer(String name)
    {
        for (DataServer server : servers)
        {
            if (server.getName().equalsIgnoreCase(name))
            {
                return server;
            }
        }

        return null;
    }

    public DataServer[] getServers()
    {
        return servers;
    }

    public Establishment[] getEstablishments()
    {
        return establishments;
    }
}
