package fr.litarvan.sakado.server.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Establishment
{
    private String name;
    private FetchMethod method;

    private transient List<StudentClass> classes;

    public Establishment()
    {
        this.classes = new ArrayList<>();
    }

    public Establishment(String name, FetchMethod method)
    {
        this();

        this.name = name;
        this.method = method;
    }

    public StudentClass classOf(User user)
    {
        for (StudentClass cl : classes)
        {
            if (cl.getMembers().contains(user.getUsername()) && cl.getEstablishment() == user.getEstablishment())
            {
                return cl;
            }
        }

        return null;
    }

    public StudentClass getClassByName(String name)
    {
        for (StudentClass cl : this.classes)
        {
            if (cl.getName().equalsIgnoreCase(name))
            {
                return cl;
            }
        }

        return null;
    }

    public String getName()
    {
        return name;
    }

    public FetchMethod getMethod()
    {
        return method;
    }

    public List<StudentClass> getClasses()
    {
        return classes;
    }

    public static class FetchMethod
    {
        private String server;
        private Map<String, String> params;

        public FetchMethod()
        {
        }

        public FetchMethod(String server, Map<String, String> params)
        {
            this.server = server;
            this.params = params;
        }

        public String getServer()
        {
            return server;
        }

        public Map<String, String> getParams()
        {
            return params;
        }
    }
}
