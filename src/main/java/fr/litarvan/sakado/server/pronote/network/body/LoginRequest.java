package fr.litarvan.sakado.server.pronote.network.body;

public class LoginRequest extends TokenBody
{
    private String link;
    private String username;
    private String password;

    public LoginRequest(String token, String link, String username, String password)
    {
        super(token);

        this.link = link;
        this.username = username;
        this.password = password;
    }

    public String getLink()
    {
        return link;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }
}
