package fr.litarvan.sakado.server.http;

import fr.litarvan.sakado.server.http.controller.*;

import javax.inject.Inject;

import static spark.Spark.*;

public final class Routes
{
    @Inject
    private AuthController auth;

    public void load()
    {
        path("/auth", () -> {
            get("/login", auth::login);
        });
    }
}
