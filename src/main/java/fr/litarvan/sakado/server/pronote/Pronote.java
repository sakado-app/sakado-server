package fr.litarvan.sakado.server.pronote;

import com.google.gson.JsonObject;
import fr.litarvan.commons.config.ConfigProvider;
import fr.litarvan.sakado.server.pronote.network.DataClient;
import fr.litarvan.sakado.server.pronote.network.Response;
import fr.litarvan.sakado.server.pronote.network.Status;
import fr.litarvan.sakado.server.util.FailableConsumer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@Singleton
public class Pronote
{
    @Inject
    private ConfigProvider config;

    private DataClient client;

    public void init() throws IOException
    {
        client = new DataClient(config.at("pronote.server-host"), config.at("pronote.server-port", int.class));
        client.start();
    }

    public User login(String username, String password) throws IOException
    {
        User user = User.open(this);

        JsonObject params = new JsonObject();
        params.addProperty("username", username);
        params.addProperty("password", password);

        Response response = FailableConsumer.waitFor(future -> client.push("login", user.getToken(), params).handle(future::complete));

        if (response.getStatus() == Status.FAILED)
        {
            throw new LoginException(response.getError());
        }

        user.tryToUpdate();

        return user;
    }

    public DataClient getClient()
    {
        return client;
    }
}
