package fr.litarvan.sakado.server.pronote.network.body;

public class LoginResponse
{
    private String name;
    private String classe;
    private String avatar;

    public LoginResponse()
    {
    }

    public LoginResponse(String name, String classe, String avatar)
    {
        this.name = name;
        this.classe = classe;
        this.avatar = avatar;
    }

    public String getName()
    {
        return name;
    }

    public String getClasse()
    {
        return classe;
    }

    public String getAvatar()
    {
        return avatar;
    }
}
