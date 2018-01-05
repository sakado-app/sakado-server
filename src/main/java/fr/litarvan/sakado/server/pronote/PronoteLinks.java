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
package fr.litarvan.sakado.server.pronote;

import fr.litarvan.commons.config.ConfigProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PronoteLinks
{
    private ConfigProvider config;
    private PronoteLink[] links;

    @Inject
    public PronoteLinks(ConfigProvider config)
    {
        this.config = config;
    }

    public PronoteLink[] all()
    {
        return config.at("pronote.links", PronoteLink[].class);
    }

    public static class PronoteLink
    {
        private String name;
        private String link;

        public PronoteLink(String name, String link)
        {
            this.name = name;
            this.link = link;
        }

        public String getName()
        {
            return name;
        }

        public String getLink()
        {
            return link;
        }
    }
}
