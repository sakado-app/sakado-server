package fr.litarvan.sakado.server.classe;

import fr.litarvan.sakado.server.pronote.User;

import java.util.ArrayList;
import java.util.List;

public class ClasseManager
{
    private List<Classe> classes;

    public ClasseManager()
    {
        this.classes = new ArrayList<>();
    }

    public Classe of(User user)
    {
        for (Classe classe : classes)
        {
            if (classe.getMembers().contains(user.getUsername()))
            {
                return classe;
            }
        }

        return null;
    }

    public boolean exists(User user)
    {
        return of(user) != null;
    }
}
