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
package fr.litarvan.sakado.server.fetch;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.litarvan.paladin.ConfigManager;
import fr.litarvan.sakado.server.user.data.Averages;
import fr.litarvan.sakado.server.user.data.Homework;
import fr.litarvan.sakado.server.user.data.Subject;
import fr.litarvan.sakado.server.user.data.Week;
import javax.inject.Inject;

public class PronoteFetchMethod extends FetchMethod
{
    private ObjectMapper mapper;
    private String apiURL;

    @Inject
    public PronoteFetchMethod(ObjectMapper mapper, ConfigManager config)
    {
        this.mapper = mapper;
        this.apiURL = config.at("establishments.pronoteApiUrl");
    }

    @Override
    public FetchResponse fetch(FetchRequest request) throws IOException, FetchException
    {
        URL url = new URL(this.apiURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true);
        connection.setDoOutput(true);

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream()))
        {
            out.write(mapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8));
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

        FetchResponse response = mapper.readValue(content.toString(), FetchResponse.class);
        if (response.error != null)
        {
            throw new FetchException(response.error);
        }

        return response;
    }
}
