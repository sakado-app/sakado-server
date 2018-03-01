package fr.litarvan.sakado.server.refresh;

import fr.litarvan.commons.config.Config;
import fr.litarvan.commons.config.ConfigProvider;
import fr.litarvan.sakado.server.data.StudentClass;
import fr.litarvan.sakado.server.data.User;
import javax.inject.Inject;

public class SaveTask extends RefreshTask
{
    @Inject
    private ConfigProvider config;

    @Override
    public void refresh(User user)
    {
        Config config = this.config.get("save");
        StudentClass studentClass = user.studentClass();

        config.set("classes." + studentClass.getId() + ".representatives", studentClass.getRepresentatives());
    }
}
