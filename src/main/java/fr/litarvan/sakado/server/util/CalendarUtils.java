package fr.litarvan.sakado.server.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Locale;

public final class CalendarUtils
{
    public static String parse(Calendar calendar, int... fields)
    {
        StringBuilder result = new StringBuilder();

        for (int field : fields)
        {
            String res = calendar.getDisplayName(field, Calendar.LONG, Locale.FRANCE);

            if (res == null)
            {
                res = String.valueOf(calendar.get(field));
            }
            else
            {
                res = StringUtils.capitalize(res);
            }

            result.append(res).append(" ");
        }

        return result.substring(0, result.length() - 1);
    }
}
