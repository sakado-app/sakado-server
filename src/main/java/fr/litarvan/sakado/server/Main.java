package fr.litarvan.sakado.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.litarvan.commons.App;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main
{
    private static final Logger log = LogManager.getLogger(Main.class);

    private static Injector injector;

    public static void main(String[] args)
    {
        log.info("Creating Sakado Server...");

        injector = Guice.createInjector(new SakadoServerGuiceModule());
        App app = injector.getInstance(App.class);

        log.info("Starting...");
        System.out.println();

        app.start();
    }

    public static Injector injector()
    {
        return injector;
    }
}
