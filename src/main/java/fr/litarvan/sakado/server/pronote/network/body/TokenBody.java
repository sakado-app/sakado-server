package fr.litarvan.sakado.server.pronote.network.body;

public class TokenBody
{
    private String token;

    public TokenBody(String token)
    {
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }
}
