package fr.litarvan.sakado.server.http.error;


import fr.litarvan.commons.crash.ExceptionHandler;
import fr.litarvan.commons.crash.IReportField;
import java.util.function.Function;
import spark.Request;

public class HTTPReportField implements IReportField
{
    private String key;
    private Function<Request, String> generator;

    public HTTPReportField(String key, Function<Request, String> generator)
    {
        this.key = key;
        this.generator = generator;
    }

    @Override
    public String getKey()
    {
        return this.key;
    }

    @Override
    public String generateValue(ExceptionHandler handler, Throwable t)
    {
        if (t instanceof InRequestException)
        {
            return this.generator.apply(((InRequestException) t).getRequest());
        }

        return "None";
    }
}