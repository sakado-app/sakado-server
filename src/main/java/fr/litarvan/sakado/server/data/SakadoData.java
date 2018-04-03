package fr.litarvan.sakado.server.data;

import fr.litarvan.commons.config.ConfigProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SakadoData
{
    private DataServer[] servers;
    private Establishment[] establishments;

    @Inject
    private ConfigProvider config;

    public void init()
    {
        this.servers = config.at("data.servers", DataServer[].class);
        this.establishments = config.at("data.establishments", Establishment[].class);
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
