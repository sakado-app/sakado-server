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
package fr.litarvan.sakado.server.fetch;

import fr.litarvan.paladin.ConfigManager;
import fr.litarvan.paladin.Paladin;
import fr.litarvan.sakado.server.user.Establishment;
import fr.litarvan.sakado.server.user.User;
import fr.litarvan.sakado.server.user.UserData;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class UserFetcher
{
    private Map<String, FetchMethod> methods;

    @Inject
    public UserFetcher(Paladin paladin, ConfigManager config)
    {
        this.methods = new HashMap<>();

        Map<String, Class<? extends FetchMethod>> methods = config.at("establishments.methods");
        methods.forEach((k, v) -> this.methods.put(k, paladin.getInjector().getInstance(v)));
    }

    public void update(User user) throws IOException, FetchException
    {
        FetchResponse response = fetch(user);
        user.updateData(new UserData(response.timetable, response.homeworks, response.marks, response.averages));
    }

    public FetchResponse fetch(User user) throws IOException, FetchException
    {
        Establishment establishment = user.getEstablishment();
        return fetch(establishment.getMethod(), user.getUsername(), user.getPassword(), establishment.getLink(), establishment.getCas());
    }

    public FetchResponse fetch(String method, String username, String password, String link, String cas) throws IOException, FetchException
    {
        return methods.get(method).fetch(new FetchRequest(username, password, link, cas));
    }
}
