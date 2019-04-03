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

import java.util.ArrayList;
import java.util.List;

import fr.litarvan.sakado.server.data.Identifiable;
import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.data.UserManager;

public abstract class BaseRefreshTask extends RefreshTask
{
    private List<String> seen;

    public BaseRefreshTask()
    {
        this.seen = new ArrayList<>();
    }

    @Override
    public void refresh(UserManager userManager)
    {
        for (User user : userManager.getLoggedUsers())
        {
            if (user.getName() == null) // Logged but not fetched
            {
                continue;
            }

            refresh(user);
        }
    }

    public abstract void refresh(User user);

    protected void removeSeen(User user, List<? extends Identifiable> list)
    {
        list.removeIf(e -> {
            String id = user.getUsername() + "-" + e.getId();

            if (!seen.contains(id))
            {
                seen.add(id);
                return false;
            }

            return true;
        });
    }
}
