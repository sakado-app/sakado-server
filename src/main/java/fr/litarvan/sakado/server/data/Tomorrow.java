package fr.litarvan.sakado.server.data;

public class Tomorrow
{
    private long tomorrow;
    private Lesson[] timetable;
    private Reminder[] reminders;
    private Homework[] homeworks;

    public Tomorrow(long tomorrow, Lesson[] timetable, Reminder[] reminders, Homework[] homeworks)
    {
        this.tomorrow = tomorrow;
        this.timetable = timetable;
        this.reminders = reminders;
        this.homeworks = homeworks;
    }

    public Tomorrow()
    {
    }

    public long getTomorrow()
    {
        return tomorrow;
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
