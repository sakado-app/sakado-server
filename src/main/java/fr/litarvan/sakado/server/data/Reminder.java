package fr.litarvan.sakado.server.data;

public class Reminder
{
    private String title;
    private String content;
    private long time;

    public Reminder()
    {
    }

    public Reminder(String title, String content, long time)
    {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public String getTitle()
    {
        return title;
    }

    public String getContent()
    {
        return content;
    }

    public long getTime()
    {
        return time;
    }
}
