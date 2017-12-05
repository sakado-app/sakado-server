package fr.litarvan.sakado.server.pronote;

import java.io.IOException;

public class RequestException extends IOException
{
    public RequestException(String message)
    {
        super(message);
    }
}
