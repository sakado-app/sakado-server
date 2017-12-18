package fr.litarvan.sakado.server.routine.task;

import fr.litarvan.sakado.server.classe.Classe;
import fr.litarvan.sakado.server.pronote.Cours;
import fr.litarvan.sakado.server.pronote.User;
import fr.litarvan.sakado.server.routine.RoutineResult;
import fr.litarvan.sakado.server.routine.RoutineTask;

import java.util.ArrayList;
import java.util.List;

public class AwayCheckTask implements RoutineTask
{
    @Override
    public void apply(Classe classe)
    {
        classe.getLoggedUsers().forEach(this::check);
    }

    protected void check(User user)
    {
        RoutineResult result = new RoutineResult("away");
        List<Cours> away = new ArrayList<>();

        for (Cours cours : user.getEDT())
        {
            if ("Prof. absent".equalsIgnoreCase(cours.getInfo()))
            {
                away.add(cours);
            }
        }

        result.set("away", away);

        user.push(result);
    }
}
