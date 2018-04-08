package fr.litarvan.sakado.server.data;

public class Reminder implements Identifiable
{
    private String title;
    private String content;
    private String author;
    private long time;

    public Reminder()
    {
    }

    public Reminder(String title, String content, String author, long time)
    {
        this.title = title;
        this.content = content;
        this.author = author;
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

    public String getAuthor()
    {
        return author;
    }

    public long getTime()
    {
        return time;
    }

    @Override
    public String getId()
    {
        return "R" + this.title + ":" + this.content + ":" + getTime();
    }
}
