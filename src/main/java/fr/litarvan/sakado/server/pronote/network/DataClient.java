package fr.litarvan.sakado.server.pronote.network;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DataClient
{
    private static final Logger log = LogManager.getLogger("DataClient");

    private String address;
    private int port;

    private Socket socket;
    private BufferedReader reader;

    private Thread readThread;

    private Gson gson;

    public DataClient(String address, int port)
    {
        this.address = address;
        this.port = port;

        this.gson = new Gson();
    }

    public void start() throws IOException
    {
        if (this.reader != null)
        {
            try
            {
                this.reader.close();
            }
            catch (Exception ignored) {}
        }

        if (this.socket != null)
        {
            try
            {
                this.socket.close();
            }
            catch (Exception ignored) {}
        }

        this.socket = new Socket(address, port);
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

        this.readThread = new Thread(() -> {
            String line;

            try
            {
                while ((line = reader.readLine()) != null)
                {
                    handle(line);
                }
            }
            catch (IOException e)
            {
                log.error("Error while reading client input", e);
            }
        });
    }

    protected void handle(String line)
    {
        Response response = gson.fromJson(line, Response.class);
    }

    public String getAddress()
    {
        return address;
    }

    public int getPort()
    {
        return port;
    }
}
