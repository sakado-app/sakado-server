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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import fr.litarvan.sakado.server.data.Mark;
import fr.litarvan.sakado.server.data.Marks;
import fr.litarvan.sakado.server.data.Period;
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

		for (Marks period : user.getMarks())
		{
			for (SubjectMarks subject : period.getMarks())
			{
				allMarks.addAll(Arrays.asList(subject.getMarks()));
			}
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
            int p = mark.getPeriod();
            String period = "";

			for (Period userPeriod : user.getPeriods())
			{
				if (userPeriod.getId() == p)
				{
					period = " (" + userPeriod.getName() + ")";
				}
			}

            title = "Nouvelle note" + period;
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
