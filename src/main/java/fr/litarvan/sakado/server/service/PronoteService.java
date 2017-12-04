package fr.litarvan.sakado.server.service;

import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class PronoteService
{
    public static void main(String[] args) throws IOException
    {
        new PronoteService().auth();
    }

    public void auth() throws IOException
    {
    }
}
