package fr.litarvan.sakado.server.data.holiday;

public class NextHolidays
{
    private DayHoliday day;
    private PeriodHoliday period;

    public NextHolidays()
    {
    }

    public NextHolidays(DayHoliday day, PeriodHoliday period)
    {
        this.day = day;
        this.period = period;
    }

    public DayHoliday getDay()
    {
        return day;
    }

    public PeriodHoliday getPeriod()
    {
        return period;
    }
}
