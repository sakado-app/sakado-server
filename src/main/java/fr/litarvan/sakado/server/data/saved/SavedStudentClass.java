package fr.litarvan.sakado.server.data.saved;

import fr.litarvan.sakado.server.data.Reminder;

public class SavedStudentClass
{
    private String name;
    private String admin;
    private SavedUser[] members;
    private Reminder[] reminders;
    private String[] longHomeworks;
    private String[] representatives;

    public SavedStudentClass(String name, String admin, SavedUser[] members, Reminder[] reminders, String[] longHomeworks, String[] representatives)
    {
        this.name = name;
        this.admin = admin;
        this.members = members;
        this.reminders = reminders;
        this.longHomeworks = longHomeworks;
        this.representatives = representatives;
    }

    public String getName()
    {
        return name;
    }

    public String getAdmin()
    {
        return admin;
    }

    public SavedUser[] getMembers()
    {
        return members;
    }

    public Reminder[] getReminders()
    {
        return reminders;
    }

    public String[] getLongHomeworks()
    {
        return longHomeworks;
    }

    public String[] getRepresentatives()
    {
        return representatives;
    }
}
