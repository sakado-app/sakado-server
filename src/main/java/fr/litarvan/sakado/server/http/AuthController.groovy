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
package fr.litarvan.sakado.server.http

import fr.litarvan.paladin.Session
import fr.litarvan.paladin.http.Controller
import fr.litarvan.paladin.http.routing.*
import fr.litarvan.sakado.server.auth.AuthService
import fr.litarvan.sakado.server.auth.ForbiddenOperationException
import fr.litarvan.sakado.server.auth.LoginException
import fr.litarvan.sakado.server.user.EstablishmentManager
import fr.litarvan.sakado.server.user.User

import javax.inject.Inject

class AuthController extends Controller
{
    @Inject
    private AuthService auth;

    @Inject
    private EstablishmentManager establishments;

    @RequestParams(required = ["establishment", "username", "password", "deviceToken"], optional = ["deviceToken"])
    def login(String establishmentName, String username, String password, String deviceToken, Session session) throws LoginException
    {
        def establishment = establishments[establishmentName]

        User user = auth.login(establishment, username, password)
        user.deviceToken = deviceToken
        user.session = session

        session.set(User, user)

        refresh(user) + [ token: session.token ]
    }

    def refresh(User user)
    {
        if (user == null || !user.isLogged())
        {
            throw new ForbiddenOperationException();
        }

        [
            username: user.username,
            fullName: user.fullName,
            studentClass: user.studentClassName,
            avatar: user.avatar,

            admin: user.admin,
            representative: user.representative,

            timetable: user.timetable,
            homeworks: user.homeworks,
            marks: user.marks,
            averages: user.averages
        ]
    }
    
    def logout(User user) throws ForbiddenOperationException
    {
        if (user == null)
        {
            throw new ForbiddenOperationException();
        }

        auth.logout(user)

        true
    }
}
