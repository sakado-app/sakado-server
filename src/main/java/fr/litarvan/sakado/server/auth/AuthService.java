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
package fr.litarvan.sakado.server.auth;

import fr.litarvan.paladin.SessionManager;
import fr.litarvan.sakado.server.fetch.FetchException;
import fr.litarvan.sakado.server.user.*;
import fr.litarvan.sakado.server.fetch.FetchResponse;
import fr.litarvan.sakado.server.fetch.UserFetcher;

import javax.inject.Inject;

public class AuthService
{
    @Inject
    private UserFetcher userFetcher;

    @Inject
    private SessionManager sessionManager;

    public User login(Establishment establishment, String username, String password) throws LoginException
    {
        if (establishment == null)
        {
            throw new IllegalArgumentException("establishment == null");
        }

        User user = establishment.getUser(username);

        if (user == null)
        {
            FetchResponse response;

            try
            {
                response = userFetcher.fetch(establishment.getMethod(), username, password, establishment.getLink(), establishment.getCas());
            }
            catch (FetchException e)
            {
                throw new LoginException("Login error : " + e.getMessage(), e);
            }

            StudentClass studentClass = establishment.getClass(response.studentClass);

            if (studentClass == null)
            {
                studentClass = new StudentClass(response.studentClass);
            }

            user = new User(username, password, response.name, studentClass, response.avatar, establishment, UserData.fromResponse(response));

            if (studentClass.getAdmin() == null)
            {
                studentClass.add(user);
                user.setAdmin(true);
            }
        }
        else
        {
            FetchResponse response;

            try
            {
                response = userFetcher.fetch(user);
            }
            catch (FetchException e)
            {
                throw new LoginException("Login error : " + e.getMessage(), e);
            }

            user.updateData(UserData.fromResponse(response));
        }

        return user;
    }

    public void logout(User user) throws IllegalArgumentException, ForbiddenOperationException
    {
        if (user == null)
        {
            throw new IllegalArgumentException("user == null");
        }

        if (!user.isLogged())
        {
            throw new ForbiddenOperationException();
        }

        sessionManager.remove(user.getSession());
        user.logout();
    }
}
