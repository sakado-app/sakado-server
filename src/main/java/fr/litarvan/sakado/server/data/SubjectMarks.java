package fr.litarvan.sakado.server.data;

public class SubjectMarks
{
    private String name;
    private float average;
    private float studentClassAverage;
    private float maxAverage;
    private float minAverage;
    private Mark[] marks;

    public SubjectMarks()
    {
    }

    public SubjectMarks(String name, float average, float studentClassAverage, float maxAverage, float minAverage, Mark[] marks)
    {
        this.name = name;
        this.average = average;
        this.studentClassAverage = studentClassAverage;
        this.maxAverage = maxAverage;
        this.minAverage = minAverage;
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

    public float getStudentClassAverage()
    {
        return studentClassAverage;
    }

    public float getMaxAverage()
    {
        return maxAverage;
    }

    public float getMinAverage()
    {
        return minAverage;
    }

    public Mark[] getMarks()
    {
        return marks;
    }
}
