package fr.litarvan.sakado.server.pronote;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static fr.litarvan.sakado.server.util.FailableConsumer.waitFor;

public class User
{
    private static final Logger log = LogManager.getLogger("Pronote");

    private String token;

    private Pronote pronote;
    private Cours[] edt;

    protected User(Pronote pronote, String token)
    {
        this.pronote = pronote;
        this.token = token;
    }

    static User open(Pronote pronote) throws IOException
    {
        return waitFor(future -> pronote.getClient().push("open").handle(response -> {
            future.complete(new User(pronote, response.getResult().get("Token").getAsString()));
        }));
    }

    public void update()
    {
        try
        {
            tryToUpdate();
        }
        catch (IOException e)
        {
            log.error("Couldn't query user data from Pronote, ignoring", e);
        }
    }

    public void tryToUpdate() throws IOException
    {
        Gson gson = new Gson();

        this.edt = waitFor(future -> pronote.getClient().push("edt").handle(r -> {
            JsonArray array = r.getResult().getAsJsonArray();
            Cours[] cours = new Cours[array.size()];

            for (int i = 0; i < array.size(); i++)
            {
                cours[i] = gson.fromJson(array.get(i), Cours.class);
            }

            future.complete(cours);
        }));
    }

    String getToken()
    {
        return token;
    }

    public Cours[] getEDT()
    {
        if (edt == null)
        {
            update();
        }

        return edt;
    }
}
