package fr.litarvan.sakado.server.refresh;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.litarvan.sakado.server.data.Reminder;
import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.push.PushService;
import fr.litarvan.sakado.server.push.PushType;
import fr.litarvan.sakado.server.util.CalendarUtils;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReminderTask extends RefreshTask
{
    private static final Logger log = LogManager.getLogger("ReminderTask");

    @Inject
    private PushService push;

    @Override
    public void refresh(User user)
    {
        List<Reminder> reminders = new ArrayList<>();

        reminders.addAll(user.getReminders());
        reminders.addAll(user.studentClass().getReminders());

        reminders.removeIf(reminder -> {
            Calendar date = CalendarUtils.fromTimestamp(reminder.getTime());
            Calendar current = CalendarUtils.create();

            return current.get(Calendar.DAY_OF_MONTH) != date.get(Calendar.DAY_OF_MONTH) - 1 || current.get(Calendar.MONTH) != date.get(Calendar.MONTH) || current.get(Calendar.HOUR_OF_DAY) < 19;
        });

        removeSeen(user, reminders);

        if (reminders.size() == 0)
        {
            return;
        }

        String title;
        String message;

        if (reminders.size() > 1)
        {
            title = "Multiples rappels pour demain";
            message = "Cliquer pour voir";
        }
        else
        {
            Reminder reminder = reminders.get(0);

            title = "Rappel pour demain";
            message = reminder.getTitle() + " - " + reminder.getContent();
        }

        try
        {
            push.send(user, PushType.REMINDER, title, message);
        }
        catch (Exception e)
        {
            log.error("Couldn't send reminder push notification", e);
        }
    }
}
