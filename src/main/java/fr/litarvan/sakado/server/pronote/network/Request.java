package fr.litarvan.sakado.server.pronote.network;

public class Request
{
    private int id;
    private String token;
    private String request;
    private Object params;

    public Request(int id, String token, String request)
    {
        this.id = id;
        this.token = token;
        this.request = request;
    }

    public Request(int id, String token, String request, Object params)
    {
        this.id = id;
        this.token = token;
        this.request = request;
        this.params = params;
    }

    public int getId()
    {
        return id;
    }

    public String getToken()
    {
        return token;
    }

    public String getRequest()
    {
        return request;
    }

    public Object getParams()
    {
        return params;
    }
}
