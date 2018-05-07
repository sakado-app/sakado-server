package fr.litarvan.sakado.server.data;

public class Averages
{
    private float student;
    private float studentClass;
    private int period;

    public Averages()
    {
    }

    public Averages(float student, float studentClass, int period)
    {
        this.student = student;
        this.studentClass = studentClass;
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

    public int getPeriod()
    {
        return period;
    }
}
