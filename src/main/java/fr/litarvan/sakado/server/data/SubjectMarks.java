package fr.litarvan.sakado.server.data;

public class SubjectMarks
{
    private String name;
    private float average;
    private Mark[] marks;

    public SubjectMarks()
    {
    }

    public SubjectMarks(String name, float average, Mark[] marks)
    {
        this.name = name;
        this.average = average;
        this.marks = marks;
    }

    public String getSubject()
    {
        return name;
    }

    public float getAverage()
    {
        return average;
    }

    public Mark[] getMarks()
    {
        return marks;
    }
}
