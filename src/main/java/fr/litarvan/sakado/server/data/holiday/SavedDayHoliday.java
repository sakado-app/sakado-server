package fr.litarvan.sakado.server.data.holiday;

public class SavedDayHoliday
{
    private String name;
    private int day;
    private int month;

    public SavedDayHoliday()
    {
    }

    public SavedDayHoliday(String name, int day, int month)
    {
        this.name = name;
        this.day = day;
        this.month = month;
    }

    public String getName()
    {
        return name;
    }

    public int getDay()
    {
        return day;
    }

    public int getMonth()
    {
        return month;
    }
}
