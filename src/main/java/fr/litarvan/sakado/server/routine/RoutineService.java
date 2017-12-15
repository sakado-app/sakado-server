package fr.litarvan.sakado.server.routine;

import fr.litarvan.sakado.server.Main;
import fr.litarvan.sakado.server.SakadoServer;
import fr.litarvan.sakado.server.classe.Classe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class RoutineService
{
    private static final Logger log = LogManager.getLogger("RoutineService");
    public static final long RATE = 15 * 60 * 1000; // Every 15 minutes

    private SakadoServer server;
    private int id;
    private List<RoutineTask> tasks;

    @Inject
    public RoutineService(SakadoServer server)
    {
        this.server = server;
        this.id = 0;
        this.tasks = new ArrayList<>();
    }

    public void start()
    {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                log.info("Executing Routine #{}", id);
                id++;

                execute(server.getClasses().toArray(new Classe[server.getClasses().size()]));
            }
        }, 5 * 60 * 1000, RATE);
    }

    public void execute(Classe[] classes)
    {
        ExecutorService pool = Executors.newFixedThreadPool(classes.length);
        Stream.of(classes).forEach(cl -> pool.submit(() -> execute(cl)));

        pool.shutdown();
    }

    public void execute(Classe classe)
    {
        tasks.forEach(task -> task.apply(classe));
    }

    public void add(Class<? extends RoutineTask> task)
    {
        tasks.add(Main.injector().getInstance(task));
    }
}
