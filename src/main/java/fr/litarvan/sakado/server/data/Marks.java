package fr.litarvan.sakado.server.data;

public class Marks
{
	private SubjectMarks[] marks;
	private Averages averages;

	public Marks()
	{
	}

	public Marks(SubjectMarks[] marks, Averages averages)
	{
		this.marks = marks;
		this.averages = averages;
	}

	public SubjectMarks[] getMarks()
	{
		return marks;
	}

	public Averages getAverages()
	{
		return averages;
	}
}
