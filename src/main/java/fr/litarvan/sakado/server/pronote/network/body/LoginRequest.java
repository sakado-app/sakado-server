package fr.litarvan.sakado.server.pronote.network.body;

public class LoginRequest extends TokenBody
{
    private String username;
    private String password;

    public LoginRequest(String token, String username, String password)
    {
        super(token);

        this.username = username;
        this.password = password;
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
