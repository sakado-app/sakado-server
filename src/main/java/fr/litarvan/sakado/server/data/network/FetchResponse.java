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
package fr.litarvan.sakado.server.data.network;

import java.util.Map;

import fr.litarvan.sakado.server.data.CompleteFileUpload;
import fr.litarvan.sakado.server.data.Homework;
import fr.litarvan.sakado.server.data.Marks;
import fr.litarvan.sakado.server.data.Report;
import fr.litarvan.sakado.server.data.Week;

public class FetchResponse extends Response
{
    private String studentClass;
    private String name;
    private String avatar;

    private Week[] timetable;

    private String[][] menu;

    private Map<String, Marks> marks;
    private Homework[] homeworks;
	private Map<String, Report> reports;

    private CompleteFileUpload[] files;

    private int defaultPeriod;

    public FetchResponse()
    {
    }

	public FetchResponse(String studentClass, String name, String avatar, Week[] timetable, String[][] menu, Map<String, Marks> marks, Homework[] homeworks, Map<String, Report> reports, CompleteFileUpload[] files, int defaultPeriod)
	{
		this.studentClass = studentClass;
		this.name = name;
		this.avatar = avatar;
		this.timetable = timetable;
		this.menu = menu;
		this.marks = marks;
		this.homeworks = homeworks;
		this.reports = reports;
		this.files = files;
		this.defaultPeriod = defaultPeriod;
	}

	public String getStudentClass()
    {
        return studentClass;
    }

    public String getName()
    {
        return name;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public Week[] getTimetable()
    {
        return timetable;
    }

    public String[][] getMenu()
    {
        return menu;
    }

	public Map<String, Marks> getMarks()
	{
		return marks;
	}

	public Homework[] getHomeworks()
    {
        return homeworks;
    }

	public Map<String, Report> getReports()
	{
		return reports;
	}

	public CompleteFileUpload[] getFiles()
	{
		return files;
	}

	public int getDefaultPeriod()
	{
		return defaultPeriod;
	}
}
