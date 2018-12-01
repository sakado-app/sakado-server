package fr.litarvan.sakado.server.refresh;

import fr.litarvan.commons.config.Config;
import fr.litarvan.commons.config.ConfigProvider;
import fr.litarvan.sakado.server.data.Establishment;
import fr.litarvan.sakado.server.data.SakadoData;
import fr.litarvan.sakado.server.data.StudentClass;
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

        for (Establishment establishment : data.getEstablishments())
        {
            for (StudentClass studentClass : establishment.getClasses())
            {
                config.set("classes." + studentClass.getId() + ".admin", studentClass.getAdmin());
                config.set("classes." + studentClass.getId() + ".representatives", studentClass.getRepresentatives());
                config.set("classes." + studentClass.getId() + ".reminders", studentClass.getReminders());

                for (User user : studentClass.getLoggedUsers())
                {
                    config.set("classes." + studentClass.getId() + ".users." + user.getUsername() + ".reminders", user.getReminders());
                }
            }
        }

        //config.set("loggedUsers", userManager.getLoggedUsers());
    }
}
