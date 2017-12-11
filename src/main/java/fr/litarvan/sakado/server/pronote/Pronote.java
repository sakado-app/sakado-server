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
import java.util.ArrayList;
import java.util.List;

@Singleton
public class Pronote
{
    @Inject
    private ConfigProvider config;

    private DataClient client;
    private List<User> users;

    public Pronote()
    {
        this.users = new ArrayList<>();
    }

    public void init() throws IOException
    {
        client = new DataClient(config.at("pronote.server-host"), config.at("pronote.server-port", int.class));
        client.start();
    }

    public User login(String username, String password) throws IOException
    {
        User user = get(username);

        if (user != null)
        {
            if (user.isLogged())
            {
                return user;
            }
        }
        else
        {
            user = User.open(this, username);
        }

        JsonObject params = new JsonObject();
        params.addProperty("username", username);
        params.addProperty("password", password);

        this.users.add(user);

        String token = user.getToken();
        Response response = FailableConsumer.waitFor(future -> client.push("login", token, params).handle(future::complete));
        response.doThrow();

        if (response.getStatus() == Status.FAILED)
        {
            throw new LoginException(response.getError());
        }

        user.tryToUpdate();

        return user;
    }

    public User get(String username)
    {
        for (User user : users)
        {
            if (user.getUsername().equalsIgnoreCase(username))
            {
                return user;
            }
        }

        return null;
    }

    public User getByToken(String token)
    {
        for (User user : users)
        {
            if (user.getToken().equals(token))
            {
                return user;
            }
        }

        return null;
    }

    public DataClient getClient()
    {
        return client;
    }
}
