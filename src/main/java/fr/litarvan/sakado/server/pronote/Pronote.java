package fr.litarvan.sakado.server.pronote;

import fr.litarvan.commons.config.ConfigProvider;
import fr.litarvan.sakado.server.pronote.network.DataClient;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Pronote
{
    @Inject
    private ConfigProvider config;

    private DataClient client;

    public void init()
    {

    }

    public DataClient getClient()
    {
        return client;
    }
}
