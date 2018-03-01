package fr.litarvan.sakado.server.refresh;

import java.util.ArrayList;
import java.util.List;

import fr.litarvan.sakado.server.data.Identifiable;
import fr.litarvan.sakado.server.data.User;

public abstract class RefreshTask
{
    private List<String> seen;

    public RefreshTask()
    {
        this.seen = new ArrayList<>();
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
