package fr.litarvan.sakado.server.refresh;

import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.data.UserManager;
import javax.inject.Inject;

public class DisconnectExpiredUsersTask extends RefreshTask
{
    public static final long MAX_LAST_LOGIN = 7 * 24 * 60 * 60 * 1000;

    @Inject
    private UserManager userManager;

    @Override
    public void refresh(User user)
    {
        if (System.currentTimeMillis() - MAX_LAST_LOGIN > user.getLastLogin())
        {
            userManager.remove(user);
        }
    }
}
