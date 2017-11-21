package fr.litarvan.sakado.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import fr.litarvan.commons.App;

public class SakadoServerGuiceModule extends AbstractModule
{
    protected void configure()
    {
        bind(App.class).to(SakadoServer.class);
        bind(Gson.class).toInstance(new GsonBuilder().setPrettyPrinting().create());
    }
}
