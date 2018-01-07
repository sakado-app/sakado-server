/*
 *  Sakado, an app for school
 *  Copyright (c) 2017-2018 Adrien 'Litarvan' Navratil
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