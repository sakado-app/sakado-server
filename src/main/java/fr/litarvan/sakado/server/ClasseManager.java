package fr.litarvan.sakado.server;

import fr.litarvan.sakado.server.pronote.User;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ClasseManager
{
    private List<Classe> classes;

    public ClasseManager()
    {
        this.classes = new ArrayList<>();
    }

    public void add(Classe classe)
    {
        this.classes.add(classe);
    }

    public Classe get(String pronoteLink, String name)
    {
        for (Classe classe : classes)
        {
            if (classe.getPronoteUrl().equalsIgnoreCase(pronoteLink) && classe.getName().equalsIgnoreCase(name))
            {
                return classe;
            }
        }

        return null;
    }

    public Classe of(User user)
    {
        for (Classe classe : classes)
        {
            if (classe.getMembers().contains(user.getUsername()) && classe.getPronoteUrl().equalsIgnoreCase(user.getPronoteUrl()))
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

    public Classe[] getClasses()
    {
        return classes.toArray(new Classe[classes.size()]);
    }
}
