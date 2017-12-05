package fr.litarvan.sakado.server.pronote.network;

import com.google.gson.JsonObject;

public class Response
{
    private int id;

    private Status status;
    private String error;
    private JsonObject result;

    Response(int id, Status status, String error, JsonObject result)
    {
        this.id = id;
        this.status = status;
        this.error = error;
        this.result = result;
    }

    int getId()
    {
        return id;
    }

    public Status getStatus()
    {
        return status;
    }

    public String getError()
    {
        return error;
    }

    public JsonObject getResult()
    {
        return result;
    }
}
