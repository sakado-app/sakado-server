package fr.litarvan.sakado.server.pronote.network;

public class Response
{
    private int id;
    private String status;
    private Object result;

    public Response(int id, String status)
    {
        this.id = id;
        this.status = status;
    }

    public int getId()
    {
        return id;
    }

    public String getStatus()
    {
        return status;
    }

    public Object getResult()
    {
        return result;
    }
}
