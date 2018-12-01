package fr.litarvan.sakado.server.refresh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import fr.litarvan.sakado.server.data.Mark;
import fr.litarvan.sakado.server.data.SubjectMarks;
import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.push.PushService;
import fr.litarvan.sakado.server.push.PushType;
import fr.litarvan.sakado.server.util.CalendarUtils;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewMarkTask extends BaseRefreshTask
{
    private static final Logger log = LogManager.getLogger("NewMarkTask");

    @Inject
    private PushService push;

    @Override
    public void refresh(User user)
    {
        Calendar max = CalendarUtils.create();
        max.add(Calendar.DAY_OF_MONTH, -7);

        List<Mark> marks = new ArrayList<>();
        List<Mark> allMarks = new ArrayList<>();

        for (SubjectMarks subject : user.getMarks())
        {
            allMarks.addAll(Arrays.asList(subject.getMarks()));
        }

        for (Mark mark : allMarks)
        {
            if (mark.getTimeAsCalendar().after(max))
            {
                marks.add(mark);
            }
        }

        removeSeen(user, marks);

        if (marks.size() == 0)
        {
            return;
        }

        String title;
        String message;

        if (marks.size() > 1)
        {
            title = "Nouvelles notes";
            message = "Cliquer pour voir";
        }
        else
        {
            Mark mark = marks.get(0);

            title = "Nouvelle note";
            message = mark.getSubject() + " - " + mark.getValue() + "/" + mark.getMax() + (mark.getTitle() != null ? " (" + mark.getTitle() + ")" : "");
        }

        try
        {
            push.send(user, PushType.MARK, title, message);
        }
        catch (Exception e)
        {
            log.error("Couldn't send mark push notification", e);
        }
    }
}
