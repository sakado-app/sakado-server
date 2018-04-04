package fr.litarvan.sakado.server.data;

public class Averages
{
    private float student;
    private float studentClass;
    private Average[] subjects;
    private int period;

    public Averages()
    {
    }

    public Averages(float student, float studentClass, Average[] subjects, int period)
    {
        this.student = student;
        this.studentClass = studentClass;
        this.subjects = subjects;
        this.period = period;
    }

    public float getStudent()
    {
        return student;
    }

    public float getStudentClass()
    {
        return studentClass;
    }

    public Average[] getSubjects()
    {
        return subjects;
    }

    public int getPeriod()
    {
        return period;
    }
}
