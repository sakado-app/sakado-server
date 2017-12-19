package fr.litarvan.sakado.server.http.controller;

import fr.litarvan.sakado.server.http.Controller;
import fr.litarvan.sakado.server.http.error.APIError;
import fr.litarvan.sakado.server.pronote.PronoteLinks;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

public class MainController extends Controller
{
    @Inject
    private PronoteLinks links;

    public Object poll(Request request, Response response) throws APIError
    {
        return json(requireLogged(request).poll(), response);
    }

    public Object links(Request request, Response response) throws APIError
    {
        return json(links.all(), response);
    }
}
