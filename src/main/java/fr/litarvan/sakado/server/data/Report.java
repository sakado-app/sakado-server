package fr.litarvan.sakado.server.data;

public class Report
{
	private int period;
	private ReportSubject[] subjects;
	private Averages averages;
	private Comment[] comments;

	public Report()
	{
	}

	public Report(int period, ReportSubject[] subjects, Averages averages, Comment[] comments)
	{
		this.period = period;
		this.subjects = subjects;
		this.averages = averages;
		this.comments = comments;
	}

	public int getPeriod()
	{
		return period;
	}

	public ReportSubject[] getSubjects()
	{
		return subjects;
	}

	public Averages getAverages()
	{
		return averages;
	}

	public Comment[] getComments()
	{
		return comments;
	}

	public static class ReportSubject
	{
		private String name;
		private float average;
		private float studentClassAverage;
		private float maxAverage;
		private float minAverage;
		private String comment;
		private float coefficient;

		public ReportSubject()
		{
		}

		public ReportSubject(String name, float average, float studentClassAverage, float maxAverage, float minAverage, String comment, float coefficient)
		{
			this.name = name;
			this.average = average;
			this.studentClassAverage = studentClassAverage;
			this.maxAverage = maxAverage;
			this.minAverage = minAverage;
			this.comment = comment;
			this.coefficient = coefficient;
		}

		public String getName()
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

		public String getComment()
		{
			return comment;
		}

		public float getCoefficient()
		{
			return coefficient;
		}
	}

	public static class Comment
	{
		private String title;
		private String value;

		public Comment()
		{
		}

		public Comment(String title, String value)
		{
			this.title = title;
			this.value = value;
		}

		public String getTitle()
		{
			return title;
		}

		public String getValue()
		{
			return value;
		}
	}
}