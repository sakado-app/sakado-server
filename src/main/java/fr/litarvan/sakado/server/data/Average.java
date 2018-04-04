package fr.litarvan.sakado.server.data;

public class Average
{
    private String subject;
    private float value;

    public Average()
    {
    }

    public Average(String subject, float value)
    {
        this.subject = subject;
        this.value = value;
    }

    public String getSubject()
    {
        return subject;
    }

    public float getValue()
    {
        return value;
    }
}
