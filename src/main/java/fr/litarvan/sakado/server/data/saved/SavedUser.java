package fr.litarvan.sakado.server.data.saved;

public class SavedUser
{
    private String token;
    private String establishment;
    private String username;
    private String password;
    private long lastLogin;

    public SavedUser(String token, String establishment, String username, String password, long lastLogin)
    {
        this.token = token;
        this.establishment = establishment;
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;
    }

    public String getToken()
    {
        return token;
    }

    public String getEstablishment()
    {
        return establishment;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public long getLastLogin()
    {
        return lastLogin;
    }
}
