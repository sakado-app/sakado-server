package fr.litarvan.sakado.server.data.holiday;

public class NextHolidays
{
    private DayHoliday day;
    private long untilDay;
    private PeriodHoliday period;
    private long untilPeriod;

    public NextHolidays()
    {
    }

    public NextHolidays(DayHoliday day, long untilDay, PeriodHoliday period, long untilPeriod)
    {
        this.day = day;
        this.untilDay = untilDay;
        this.period = period;
        this.untilPeriod = untilPeriod;
    }

    public DayHoliday getDay()
    {
        return day;
    }

    public long getUntilDay()
    {
        return untilDay;
    }

    public PeriodHoliday getPeriod()
    {
        return period;
    }

    public long getUntilPeriod()
    {
        return untilPeriod;
    }
}
