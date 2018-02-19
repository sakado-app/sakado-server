package fr.litarvan.sakado.server.data;

import fr.litarvan.commons.config.ConfigProvider;

import javax.inject.Inject;
import java.io.IOException;

public class SakadoData
{
    private DataServer[] servers;
    private Establishment[] establishments;

    @Inject
    public SakadoData(ConfigProvider config)
    {
        this.servers = config.at("data.servers", DataServer[].class);
        this.establishments = config.at("data.establishments", Establishment[].class);
    }

    public void init() throws IOException
    {
        for (DataServer server : servers)
        {
            server.init();
        }
    }

    public Establishment getEstablishment(String name)
    {
        for (Establishment establishment : establishments)
        {
            if (establishment.getName().equalsIgnoreCase(name))
            {
                return establishment;
            }
        }

        return null;
    }

    public DataServer getServer(String name)
    {
        for (DataServer server : servers)
        {
            if (server.getName().equalsIgnoreCase(name))
            {
                return server;
            }
        }

        return null;
    }

    public DataServer[] getServers()
    {
        return servers;
    }

    public Establishment[] getEstablishments()
    {
        return establishments;
    }
}
