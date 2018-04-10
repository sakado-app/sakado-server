package fr.litarvan.sakado.server.data.holiday;

public class SavedPeriodHoliday
{
    private String name;
    private Day from;
    private Day to;

    public SavedPeriodHoliday()
    {
    }

    public SavedPeriodHoliday(String name, Day from, Day to)
    {
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public String getName()
    {
        return name;
    }

    public Day getFrom()
    {
        return from;
    }

    public Day getTo()
    {
        return to;
    }

    public static class Day
    {
        private int day;
        private int month;

        public Day()
        {
        }

        public Day(int day, int month)
        {
            this.day = day;
            this.month = month;
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
}
