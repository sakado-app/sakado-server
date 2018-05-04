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
package fr.litarvan.sakado.server.user;

import fr.litarvan.paladin.ConfigManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Objects;

@Singleton
public class EstablishmentManager
{
    private List<Establishment> establishments;

    @Inject
    public EstablishmentManager(ConfigManager config)
    {
        this.establishments = config.at("establishments.establishments");
    }

    public Establishment getAt(String name)
    {
        for (Establishment establishment : establishments)
        {
            if (Objects.equals(establishment.getName(), name))
            {
                return establishment;
            }
        }

        return null;
    }
}
