package fr.litarvan.sakado.server.data;

public class SubjectMarks
{
    private String subject;
    private Mark[] marks;

    public SubjectMarks()
    {
    }

    public SubjectMarks(String subject, Mark[] marks)
    {
        this.subject = subject;
        this.marks = marks;
    }

    public String getSubject()
    {
        return subject;
    }

    public Mark[] getMarks()
    {
        return marks;
    }
}
