package fr.litarvan.sakado.server.http.error;

import com.google.gson.Gson;
import fr.litarvan.sakado.server.Main;
import spark.Response;

public class APIError extends Exception
{
    // Auth
    public static final String INVALID_CREDENTIALS = "Invalid credentials";

    // HTTP
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String MISSING_PARAMETER = "Missing parameter";

    private static Gson gson = Main.injector().getInstance(Gson.class);

    public static final String NOT_FOUND = "Not Found";

    private String title;
    private String message;

    public APIError(String title, String message)
    {
        super(message);

        this.title = title;
        this.message = message;
    }

    public String response(Response response)
    {
        String rep = gson.toJson(this);

        response.type("application/json");
        response.body(rep);

        return rep;
    }

    public String getTitle()
    {
        return title;
    }

    public String getMessage()
    {
        return message;
    }
}