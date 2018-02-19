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
package fr.litarvan.sakado.server.pronote;

import fr.litarvan.commons.config.ConfigProvider;
import fr.litarvan.sakado.server.StudentClass;
import fr.litarvan.sakado.server.ClassManager;
import fr.litarvan.sakado.server.pronote.network.NetworkClient;
import fr.litarvan.sakado.server.pronote.network.RequestException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class Pronote
{
    private static final Logger log = LogManager.getLogger("Pronote");

    @Inject
    private ConfigProvider config;

    @Inject
    private ClassManager classManager;

    private NetworkClient client;
    private List<User> users;

    public Pronote()
    {
        this.users = new ArrayList<>();
    }

    public void init() throws IOException
    {
        client = new NetworkClient(config.at("pronote.server-host"), config.at("pronote.server-port", int.class));
    }

    public User login(String link, String username, String password, String deviceToken) throws IOException, RequestException
    {
        if (!link.startsWith("http://") && !link.startsWith("https://"))
        {
            link = "http://" + link;
        }

        if (link.endsWith("eleve.html"))
        {
            link = link.substring(0, link.length() - 10);
        }

        if (!link.endsWith("/"))
        {
            link += "/";
        }

        log.info("Logging in '{}' (from {})", username, link);
        User user = new User(this, RandomStringUtils.randomAlphanumeric(128), link, username, password, deviceToken);
        user.update();

        User current = get(username);

        if (current != null)
        {
            this.remove(current);
        }

        this.users.add(user);

        log.info("Successfully logged user '{}' : {} ({})", username, user.getName(), user.getStudentClass());

        StudentClass studentClass = classManager.of(user);

        if (studentClass == null)
        {
            studentClass = classManager.get(link, user.getStudentClass());

            if (studentClass == null)
            {
                studentClass = new StudentClass(link, user.getStudentClass(), user.getUsername());
                classManager.add(studentClass);

                log.info("Created class '{}' on {} (for {}) ", user.getStudentClass(), link, user.getName());
            }

            log.info("Added '{}' to class '{}' on {}", user.getName(), user.getStudentClass(), link);
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
        this.classManager.of(user).getLoggedUsers().remove(user);
        this.users.remove(user);
    }

    public NetworkClient getClient()
    {
        return client;
    }
}
