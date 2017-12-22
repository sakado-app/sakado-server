package fr.litarvan.sakado.server.routine;

import java.util.HashMap;
import java.util.Map;

public class RoutineResult
{
    private String name;
    private Map<String, Object> result;
    private boolean seen;

    public RoutineResult(String name)
    {
        this.name = name;
        this.result = new HashMap<>();
    }

    public boolean isSeen()
    {
        return seen;
    }

    public void setSeen()
    {
        this.seen = true;
    }

    public void set(String str, Object object)
    {
        result.put(str, object);
    }
}
