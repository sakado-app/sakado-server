package fr.litarvan.sakado.server.data;

public class Marks
{
	private int period;
	private SubjectMarks[] marks;
	private Averages averages;

	public Marks()
	{
	}

	public Marks(int period, SubjectMarks[] marks, Averages averages)
	{
		this.period = period;
		this.marks = marks;
		this.averages = averages;
	}

	public int getPeriod()
	{
		return period;
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
