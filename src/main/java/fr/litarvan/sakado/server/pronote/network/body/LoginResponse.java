package fr.litarvan.sakado.server.pronote.network.body;

public class LoginResponse
{
    private String name;
    private String classe;

    public LoginResponse()
    {
    }

    public LoginResponse(String name, String classe)
    {
        this.name = name;
        this.classe = classe;
    }

    public String getName()
    {
        return name;
    }

    public String getClasse()
    {
        return classe;
    }
}
