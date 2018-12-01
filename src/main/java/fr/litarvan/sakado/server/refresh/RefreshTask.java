package fr.litarvan.sakado.server.refresh;

import fr.litarvan.sakado.server.data.UserManager;

public abstract class RefreshTask
{
    public abstract void refresh(UserManager userManager);
}
