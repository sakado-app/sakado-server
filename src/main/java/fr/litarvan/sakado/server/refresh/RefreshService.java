/*
 *  Sakado, an app for school
 *  Copyright (c) 2017-2018 Adrien 'Litarvan' Navratil
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package fr.litarvan.sakado.server.refresh;

import java.util.Timer;
import java.util.TimerTask;

import fr.litarvan.commons.config.ConfigProvider;
import fr.litarvan.sakado.server.Main;
import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.data.UserManager;
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
        DeleteExpiredRemindersTask.class,
        DisconnectExpiredUsersTask.class,
        ReminderTask.class,

        SaveTask.class
    };

    @Inject
    private ConfigProvider config;

    @Inject
    private UserManager users;

    private RefreshTask[] tasks;

    public RefreshService()
    {
        this.tasks = getTasks();
    }

    public void start()
    {
        int rate = config.at("data.refresh-rate", int.class);
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

    private void refresh()
    {
        User[] users = this.users.getLoggedUsers();
        log.info("Refreshing {} users", users.length);

        for (User user : users)
        {
        	if (user.getName() == null) // Logged, but not fetched
        	{
        		continue;
			}

            log.info("Refreshing user '{}'...", user.getName());
            try
            {
                user.update();
                log.info("Data of user '{}' refreshed, starting {} tasks...", user.getName(), tasks.length);
            }
            catch (Exception e)
            {
                log.error("Exception while updating user data of '" + user.getName() + "', skipping refreshing", e);
            }
        }

        for (RefreshTask task : tasks)
        {
            try
            {
                task.refresh(this.users);
            }
            catch (Exception e)
            {
                log.error("Exception during task '" + task.getClass().getSimpleName() + "'", e);
            }
        }

        log.info("Refreshing done");
    }

    protected RefreshTask[] getTasks()
    {
        RefreshTask[] tasks = new RefreshTask[TASKS.length];

        for (int i = 0; i < TASKS.length; i++)
        {
            Object object = Main.injector().getInstance(TASKS[i]);

            if (!(object instanceof RefreshTask))
            {
                throw new IllegalArgumentException(object.getClass().getName() + " must be a RefreshTask");
            }

            tasks[i] = (RefreshTask) object;
        }

        return tasks;
    }
}
