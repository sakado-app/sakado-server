package fr.litarvan.sakado.server.data;

public class Period
{
	private int id;
	private String name;
	private boolean isDefault;

	public Period()
	{
	}

	public Period(int id, String name, boolean isDefault)
	{
		this.id = id;
		this.name = name;
		this.isDefault = isDefault;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public boolean isDefault()
	{
		return isDefault;
	}
}
