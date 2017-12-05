package fr.litarvan.sakado.server.util;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@FunctionalInterface
public interface FailableConsumer<T, E extends Throwable>
{
    void accept(T param) throws E;

    static <T> T waitFor(FailableConsumer<CompletableFuture<T>, IOException> consumer) throws IOException
    {
        CompletableFuture<T> future = new CompletableFuture<>();
        consumer.accept(future);

        try
        {
            return future.get();
        }
        catch (InterruptedException | ExecutionException ignored)
        {
        }

        // Normally can't happen
        return null;
    }
}
