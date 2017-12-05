package fr.litarvan.sakado.server.pronote;

public class Cours
{
    private String info;

    private String name;
    private String prof;
    private String salle;

    private int day;
    private int hour;

    Cours(String info, String name, String prof, String salle, int day, int hour)
    {
        this.info = info;
        this.name = name;
        this.prof = prof;
        this.salle = salle;
        this.day = day;
        this.hour = hour;
    }

    public String getInfo()
    {
        return info;
    }

    public String getName()
    {
        return name;
    }

    public String getProf()
    {
        return prof;
    }

    public String getSalle()
    {
        return salle;
    }

    public int getDay()
    {
        return day;
    }

    public int getHour()
    {
        return hour;
    }
}
