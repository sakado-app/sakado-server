package fr.litarvan.sakado.server.data;

public class CompleteFileUpload extends FileUpload
{
	private long time;
	private String subject;

	public CompleteFileUpload()
	{
		super();
	}

	public CompleteFileUpload(String name, String url, long time, String subject)
	{
		super(name, url);

		this.time = time;
		this.subject = subject;
	}

	public long getTime()
	{
		return time;
	}

	public String getSubject()
	{
		return subject;
	}
}
