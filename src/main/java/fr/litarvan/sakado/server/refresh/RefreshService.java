package fr.litarvan.sakado.server.refresh;

import java.util.Timer;
import java.util.TimerTask;

import fr.litarvan.commons.config.ConfigProvider;
import fr.litarvan.sakado.server.Main;
import fr.litarvan.sakado.server.data.StudentClass;
import fr.litarvan.sakado.server.data.Establishment;
import fr.litarvan.sakado.server.data.SakadoData;
import fr.litarvan.sakado.server.data.User;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Singleton
public class RefreshService
{
    private static final Logger log = LogManager.getLogger("RefreshService");

    private static final Class[] TASKS = {
        AwayTeacherTask.class,
        LongHomeworkTask.class,
        NewMarkTask.class,

        SaveTask.class
    };

    @Inject
    private ConfigProvider config;

    @Inject
    private SakadoData data;

    private RefreshTask[] tasks;

    public RefreshService()
    {
        this.tasks = getTasks();
    }

    public void start()
    {
        int rate = config.at("app.refresh-rate", int.class);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                refresh();
            }
        }, rate, rate);

        log.info("Started refresh service at a rate of " + rate + "ms");
    }

    protected void refresh()
    {
        for (Establishment establishment : data.getEstablishments())
        {
            for (StudentClass studentClass : establishment.getClasses())
            {
                studentClass.getLoggedUsers().forEach(this::refresh);
            }
        }
    }

    private void refresh(User user)
    {
        try
        {
            user.update();
        }
        catch (Exception e)
        {
            log.error("Exception while updating user data of '" + user.getName() + "', skipping refreshing", e);
            return;
        }

        for (RefreshTask task : tasks)
        {
            try
            {
                task.refresh(user);
            }
            catch (Exception e)
            {
                log.error("Exception during task '" + task.getClass().getSimpleName() + "' for user '" + user.getName() + "'", e);
            }
        }
    }

    protected RefreshTask[] getTasks()
    {
        RefreshTask[] tasks = new RefreshTask[TASKS.length];

        for (int i = 0; i < TASKS.length; i++)
        {
            Object object = Main.injector().getInstance(TASKS[i]);

            if (!RefreshTask.class.isInstance(object))
            {
                throw new IllegalArgumentException(object.getClass().getName() + " must be a runnable");
            }

            tasks[i] = (RefreshTask) object;
        }

        return tasks;
    }
}
