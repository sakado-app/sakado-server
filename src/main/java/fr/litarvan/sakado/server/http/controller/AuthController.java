package fr.litarvan.sakado.server.http.controller;

import fr.litarvan.sakado.server.http.Controller;
import fr.litarvan.sakado.server.http.error.APIError;
import fr.litarvan.sakado.server.pronote.LoginException;
import fr.litarvan.sakado.server.pronote.Pronote;
import fr.litarvan.sakado.server.pronote.User;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.io.IOException;

public class AuthController extends Controller
{
    @Inject
    private Pronote pronote;

    public Object login(Request request, Response response) throws IOException, APIError
    {
        String username = require(request, "username");
        String password = require(request, "password");

        User user;

        try
        {
            user = pronote.login(username, password);
        }
        catch (LoginException e)
        {
            throw new APIError(APIError.INVALID_CREDENTIALS, e.getMessage());
        }

        return success(response);
    }
}
