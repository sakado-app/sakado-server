package fr.litarvan.sakado.server.classe;

import fr.litarvan.sakado.server.pronote.User;

import java.util.ArrayList;
import java.util.List;

public class Classe
{
    private String pronoteUrl;
    private String name;
    private ArrayList<String> members;
    private transient ArrayList<User> loggedUsers;

    public Classe(String pronoteUrl, String name)
    {
        this(pronoteUrl, name, new ArrayList<>());
    }

    public Classe(String pronoteUrl, String name, ArrayList<String> members)
    {
        this.pronoteUrl = pronoteUrl;
        this.name = name;
        this.members = members;
        this.loggedUsers = new ArrayList<>();
    }

    public String getPronoteUrl()
    {
        return pronoteUrl;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<String> getMembers()
    {
        return members;
    }

    public ArrayList<User> getLoggedUsers()
    {
        return loggedUsers;
    }
}
