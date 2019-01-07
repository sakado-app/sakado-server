package fr.litarvan.sakado.server.data.holiday;

public class PeriodHoliday
{
    private String name;
    private long from;
    private long to;

    public PeriodHoliday()
    {
    }

    public PeriodHoliday(String name, long from, long to)
    {
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public String getName()
    {
        return name;
    }

    public long getFrom()
    {
        return from;
    }

    public void setFrom(long from)
    {
        this.from = from;
    }

    public long getTo()
    {
        return to;
    }

    public void setTo(long to)
    {
        this.to = to;
    }
}
