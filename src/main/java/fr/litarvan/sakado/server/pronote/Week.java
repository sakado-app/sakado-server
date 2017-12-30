package fr.litarvan.sakado.server.pronote;

public class Week
{
    private int from;
    private Cours[] content;

    public Week()
    {
    }

    public Week(int from, Cours[] content)
    {
        this.from = from;
        this.content = content;
    }

    public int getFrom()
    {
        return from;
    }

    public Cours[] getContent()
    {
        return content;
    }
}
