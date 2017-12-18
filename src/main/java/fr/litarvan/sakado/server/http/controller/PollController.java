package fr.litarvan.sakado.server.http.controller;

import fr.litarvan.sakado.server.http.Controller;
import fr.litarvan.sakado.server.http.error.APIError;
import fr.litarvan.sakado.server.pronote.User;
import fr.litarvan.sakado.server.routine.RoutineResult;
import spark.Request;
import spark.Response;

public class PollController extends Controller
{
    public Object poll(Request request, Response response) throws APIError
    {
        return json(requireLogged(request).poll(), response);
    }
}
