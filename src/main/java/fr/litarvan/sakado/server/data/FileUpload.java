package fr.litarvan.sakado.server.data;

public class FileUpload
{
	private String name;
	private String url;

	public FileUpload()
	{
	}

	public FileUpload(String name, String url)
	{
		this.name = name;
		this.url = url;
	}

	public String getName()
	{
		return name;
	}

	public String getUrl()
	{
		return url;
	}
}
