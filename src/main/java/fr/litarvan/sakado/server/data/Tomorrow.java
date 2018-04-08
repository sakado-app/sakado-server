package fr.litarvan.sakado.server.data;

public class Tomorrow
{
    private Lesson[] timetable;
    private Reminder[] reminders;
    private Homework[] homeworks;

    public Tomorrow(Lesson[] timetable, Reminder[] reminders, Homework[] homeworks)
    {
        this.timetable = timetable;
        this.reminders = reminders;
        this.homeworks = homeworks;
    }

    public Tomorrow()
    {
    }

    public Lesson[] getTimetable()
    {
        return timetable;
    }

    public Reminder[] getReminders()
    {
        return reminders;
    }

    public Homework[] getHomeworks()
    {
        return homeworks;
    }
}
