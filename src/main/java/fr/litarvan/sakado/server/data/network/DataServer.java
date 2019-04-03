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
package fr.litarvan.sakado.server.data.network;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DataServer
{
    private static final Gson gson = new Gson();

    private String name;
    private String url;
    private boolean storePassword;

    public DataServer()
    {
    }

    public DataServer(String name, String url, boolean storePassword)
    {
        this.name = name;
        this.url = url;
        this.storePassword = storePassword;
    }

    public LoginResponse login(DataRequest request) throws IOException, RequestException
    {
        return request(request, LoginResponse.class);
    }

    public FetchResponse fetch(DataRequest request) throws IOException, RequestException
    {
        return request(request, FetchResponse.class);
    }

    private <R extends Response> R request(Object request, Class<?> responseClass) throws IOException, RequestException
    {
        URL url = new URL(this.url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true);
        connection.setDoOutput(true);

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream()))
        {
            out.write(gson.toJson(request).getBytes(StandardCharsets.UTF_8));
        }

        StringBuilder content = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)))
        {
            String line;

            while ((line = in.readLine()) != null)
            {
                content.append(line).append(System.lineSeparator());
            }
        }

        connection.disconnect();

        R response = (R) gson.fromJson(content.toString(), responseClass);
        if (response.getError() != null)
        {
            throw new RequestException(new Exception(response.getError()));
        }

        return response;
    }

    public String getName()
    {
        return name;
    }

    public String getUrl()
    {
        return url;
    }

    public boolean shouldStorePassword()
    {
        return storePassword;
    }
}
