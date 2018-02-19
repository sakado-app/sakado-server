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
import fr.litarvan.sakado.server.StudentClass;
import fr.litarvan.sakado.server.data.network.RequestException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class UserManager
{
    private static final Logger log = LogManager.getLogger("UserManager");

    @Inject
    private ConfigProvider config;

    @Inject
    private SakadoData data;

    private List<User> users;

    public UserManager()
    {
        this.users = new ArrayList<>();
    }

    public User login(String establishmentName, String username, String password, String deviceToken) throws IOException, RequestException
    {
        Establishment establishment = data.getEstablishment(establishmentName);

        if (establishment == null)
        {
            throw new IllegalArgumentException("Unknown etablishment '" + establishmentName + "'");
        }

        log.info("Logging in '{}' (from {})", username, establishment.getName());
        User user = new User(data.getServer(establishment.getMethod().getServer()), RandomStringUtils.randomAlphanumeric(128), establishment, username, password, deviceToken);
        user.update();

        User current = get(username);

        if (current != null)
        {
            this.remove(current);
        }

        this.users.add(user);

        log.info("Successfully logged user '{}' : {} ({})", username, user.getName(), user.getStudentClass());

        StudentClass studentClass = user.studentClass();

        if (studentClass == null)
        {
            studentClass = establishment.getClassByName(user.getStudentClass());

            if (studentClass == null)
            {
                studentClass = new StudentClass(establishment, user.getStudentClass(), user.getUsername());
                establishment.getClasses().add(studentClass);

                log.info("Created class '{}' on {} (for {}) ", user.getStudentClass(), establishment.getName(), user.getName());
            }

            log.info("Added '{}' to class '{}' on {}", user.getName(), user.getStudentClass(), establishment.getName());
        }

        studentClass.add(user);

        return user;
    }

    public User get(String username)
    {
        for (User user : users)
        {
            if (user.getUsername().equalsIgnoreCase(username))
            {
                return user;
            }
        }

        return null;
    }

    public User getByToken(String token)
    {
        if (token == null)
        {
            return null;
        }

        for (User user : users)
        {
            if (user.getToken().equals(token))
            {
                return user;
            }
        }

        return null;
    }

    public void remove(User user)
    {
        user.studentClass().getLoggedUsers().remove(user);
        this.users.remove(user);
    }
}
