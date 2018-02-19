package fr.litarvan.sakado.server.data;

import fr.litarvan.sakado.server.data.network.NetworkClient;

import java.io.IOException;

public class DataServer
{
    private String name;
    private String host;
    private int port;

    private transient NetworkClient client;

    public DataServer()
    {
    }

    public DataServer(String name, String host, int port)
    {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public void init() throws IOException
    {
        this.client = new NetworkClient(host, port);
    }

    public NetworkClient getClient()
    {
        return this.client;
    }

    public String getName()
    {
        return name;
    }

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }
}
