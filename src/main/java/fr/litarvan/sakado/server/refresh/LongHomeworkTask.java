package fr.litarvan.sakado.server.refresh;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.litarvan.sakado.server.data.Homework;
import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.push.PushService;
import fr.litarvan.sakado.server.push.PushType;
import fr.litarvan.sakado.server.util.CalendarUtils;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LongHomeworkTask extends RefreshTask
{
    private static final Logger log = LogManager.getLogger("LongHomeworkTask");

    @Inject
    private PushService push;

    @Override
    public void refresh(User user)
    {
        if (user.getHomeworks() == null)
        {
            return;
        }

        List<Homework> longHomeworks = new ArrayList<>();
        Calendar current = CalendarUtils.create();

        for (Homework homework : user.getHomeworks())
        {
            if (!user.studentClass().getLongHomeworks().contains(homework.getId()))
            {
                continue;
            }

            Calendar date = homework.getUntilAsCalendar();
            date.add(Calendar.HOUR_OF_DAY, -6); // 18h, the last day before

            if (current.after(date))
            {
                longHomeworks.add(homework);
            }
        }

        removeSeen(user, longHomeworks);

        if (longHomeworks.size() == 0)
        {
            return;
        }

        String title;
        String message;

        if (longHomeworks.size() > 1)
        {
            title = longHomeworks.size() + " longs devoirs pour demain";
            message = "Cliquer pour voir";
        }
        else
        {
            title = "Long devoir pour demain";
            message = longHomeworks.get(0).getSubject() + " - " + longHomeworks.get(0).getContent();
        }

        try
        {
            push.send(user, PushType.HOMEWORK, title, message);
        }
        catch (Exception e)
        {
            log.error("Couldn't send away push notification", e);
        }
    }
}