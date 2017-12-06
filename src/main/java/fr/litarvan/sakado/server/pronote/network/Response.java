package fr.litarvan.sakado.server.pronote.network;

import com.google.gson.JsonObject;
import fr.litarvan.sakado.server.pronote.RequestException;
import org.apache.commons.lang3.StringUtils;

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

    public void doThrow() throws RequestException
    {
        if (status == Status.ERROR || status == Status.INTERNAL_ERROR)
        {
            throw new RequestException(StringUtils.capitalize(status.name().toLowerCase()).replace(" ", "") + " : " + error);
        }
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
