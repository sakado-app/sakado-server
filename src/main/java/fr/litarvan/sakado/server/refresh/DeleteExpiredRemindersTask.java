package fr.litarvan.sakado.server.refresh;

import java.util.Calendar;
import java.util.function.Predicate;

import fr.litarvan.sakado.server.data.Reminder;
import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.util.CalendarUtils;

public class DeleteExpiredRemindersTask extends RefreshTask
{
    @Override
    public void refresh(User user)
    {
        Predicate<Reminder> remover = reminder -> {
            Calendar time = CalendarUtils.fromTimestamp(reminder.getTime());
            time.add(Calendar.DAY_OF_MONTH, 1);

            return time.getTimeInMillis() < Calendar.getInstance().getTimeInMillis();
        };

        user.getReminders().removeIf(remover);
        user.studentClass().getReminders().removeIf(remover);
    }
}
