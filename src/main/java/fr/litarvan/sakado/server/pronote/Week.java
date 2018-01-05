package fr.litarvan.sakado.server.pronote;

import java.util.Calendar;
import java.util.Locale;

public class Week
{
    private int from;
    private Cours[] content;

    public Week()
    {
    }

    public Week(int from, Cours[] content)
    {
        this.from = from;
        this.content = content;
    }

    public Calendar getFrom()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, this.from);

        return cal;
    }

    public Cours[] getContent()
    {
        return content;
    }

    protected String parseWeek(int week)
    {
        Calendar current = Calendar.getInstance();

        int month = current.get(Calendar.MONTH);
        String result = "Du " + week + " " + current.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRANCE) + " au ";

        int next = week + 6;
        int max = 30 + month % 2;

        if (week > max)
        {
            next -= max;
            month++;
        }

        current.set(Calendar.MONTH, month);
        return result + next + ' ' + current.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRANCE);
    }
}
