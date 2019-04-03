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
package fr.litarvan.sakado.server.refresh;

import java.io.IOException;

import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.data.UserManager;
import fr.litarvan.sakado.server.push.PushService;
import fr.litarvan.sakado.server.push.PushType;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DisconnectExpiredUsersTask extends BaseRefreshTask
{
    private static final Logger log = LogManager.getLogger("DisconnectExpiredUsersTask");

    public static final long MAX_LAST_LOGIN = 2 * 7 * 24 * 60 * 60 * 1000; // 2 weeks

    @Inject
    private UserManager userManager;

    @Inject
    private PushService push;

    @Override
    public void refresh(User user)
    {
        if (System.currentTimeMillis() - MAX_LAST_LOGIN > user.getLastLogin())
        {
            try
            {
                push.send(user, PushType.DISCONNECT, "Déconnecté", "Vous avez été déconnecté de Sakado pour inactvité");
            }
            catch (IOException e)
            {
                log.error("Can't send disconnect notification to user '" + user.getName() + "'", e);
            }

            log.info("Disconnecting user '{}' for 2 weeks inactivity", user.getName());
            userManager.remove(user);
        }
    }
}
