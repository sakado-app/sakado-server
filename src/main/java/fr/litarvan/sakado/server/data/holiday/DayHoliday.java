package fr.litarvan.sakado.server.data.holiday;

public class DayHoliday
{
    private String name;
    private long time;

    public DayHoliday()
    {
    }

    public DayHoliday(String name, long time)
    {
        this.name = name;
        this.time = time;
    }

    public String getName()
    {
        return name;
    }

    public long getTime()
    {
        return time;
    }
}
