package fr.litarvan.sakado.server.routine;

import java.util.HashMap;
import java.util.Map;

public class RoutineResult
{
    private String name;
    private Map<String, Object> result;

    public RoutineResult(String name)
    {
        this.name = name;
        this.result = new HashMap<>();
    }

    public void set(String str, Object object)
    {
        result.put(str, object);
    }
}
