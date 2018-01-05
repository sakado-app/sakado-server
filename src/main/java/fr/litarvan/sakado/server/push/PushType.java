package fr.litarvan.sakado.server.push;

public enum PushType
{
    AWAY("#bb0000", "sakado");

    private String color;
    private String icon;

    PushType(String color, String icon)
    {
        this.color = color;
        this.icon = icon;
    }

    public String getColor()
    {
        return color;
    }

    public String getIcon()
    {
        return icon;
    }
}
