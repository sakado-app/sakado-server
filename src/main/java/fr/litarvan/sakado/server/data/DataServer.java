package fr.litarvan.sakado.server.data;

import com.google.gson.Gson;
import fr.litarvan.sakado.server.data.network.FetchRequest;
import fr.litarvan.sakado.server.data.network.FetchResponse;
import fr.litarvan.sakado.server.data.network.RequestException;

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

    public DataServer()
    {
    }

    public DataServer(String name, String url)
    {
        this.name = name;
        this.url = url;
    }

    public FetchResponse fetch(FetchRequest request) throws IOException, RequestException
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

        FetchResponse response = gson.fromJson(content.toString(), FetchResponse.class);
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
}
