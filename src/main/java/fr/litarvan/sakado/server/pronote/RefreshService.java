package fr.litarvan.sakado.server.pronote;

import fr.litarvan.sakado.server.classe.Classe;
import fr.litarvan.sakado.server.classe.ClasseManager;
import java.util.Timer;
import java.util.TimerTask;
import javax.inject.Inject;

public class RefreshService
{
    @Inject
    private ClasseManager classes;

    public static final long RATE = 5 * 60 * 1000;

    public void start()
    {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                for (Classe classe : classes.getClasses())
                {
                    classe.getLoggedUsers().forEach(User::update);
                }
            }
        }, RATE, RATE);
    }
}
