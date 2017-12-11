/*
 *  Sakado, an app for school
 *  Copyright (C) 2017 Adrien 'Litarvan' Navratil
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
