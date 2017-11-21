package fr.litarvan.sakado.server.http.error;

import spark.Request;

public class InRequestException extends Exception
{
    private Request request;

    public InRequestException(Throwable throwable, Request request)
    {
        super(throwable);
        this.request = request;
    }

    public Request getRequest()
    {
        return request;
    }
}