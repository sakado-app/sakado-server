package fr.litarvan.sakado.server.routine;

import fr.litarvan.sakado.server.classe.Classe;
import fr.litarvan.sakado.server.pronote.User;

public interface RoutineTask
{
    default void apply(Classe classe)
    {
        classe.getLoggedUsers().forEach(this::apply);
        // Pour executer la routine lors d'un log
    }

    void apply(User user);
}
