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

import fr.litarvan.commons.config.Config;
import fr.litarvan.commons.config.ConfigProvider;
import fr.litarvan.sakado.server.data.Establishment;
import fr.litarvan.sakado.server.data.Reminder;
import fr.litarvan.sakado.server.data.SakadoData;
import fr.litarvan.sakado.server.data.saved.SavedEstablishment;
import fr.litarvan.sakado.server.data.saved.SavedStudentClass;
import fr.litarvan.sakado.server.data.saved.SavedUser;
import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.data.UserManager;
import javax.inject.Inject;

public class SaveTask extends RefreshTask
{
    @Inject
    private ConfigProvider config;

    @Inject
    private SakadoData data;

    @Override
    public void refresh(UserManager userManager)
    {
        Config config = this.config.get("save");

        List<SavedEstablishment> savedEstablishments = new ArrayList<>();

        for (Establishment establishment : data.getEstablishments())
        {
            SavedStudentClass[] studentClasses = establishment.getClasses().stream().map(studentClass -> {
                String[] members = studentClass.getMembers().toArray(new String[0]);
                ArrayList<SavedUser> memberList = new ArrayList<>();

                for (String member : members)
                {
                    for (User user : userManager.getLoggedUsers())
                    {
                    	if (user.getName() == null) // Logged, not fetched
                    	{
                    		continue;
						}

                        if (user.getName().equals(member))
                        {
                            memberList.add(new SavedUser(user.getToken(),
                                                         user.getEstablishment().getName(),
                                                         user.getMethod().getName(),
                                                         user.getUsername(),
                                                         user.getPassword(),
                                                         user.getDeviceToken(),
                                                         user.getReminders().toArray(new Reminder[0]),
                                                         user.getLastLogin()));
                            break;
                        }
                    }
                }

                return new SavedStudentClass(studentClass.getName(),
                                             studentClass.getAdmin(),
                                             memberList.toArray(new SavedUser[0]),
                                             studentClass.getReminders().toArray(new Reminder[0]),
                                             studentClass.getLongHomeworks().toArray(new String[0]),
                                             studentClass.getRepresentatives().toArray(new String[0]));
            }).toArray(SavedStudentClass[]::new);

            savedEstablishments.add(new SavedEstablishment(establishment.getName(), studentClasses));
        }

        config.set("establishments", savedEstablishments.toArray(new SavedEstablishment[0]));
    }
}
