package fr.litarvan.sakado.server.pronote.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;

public class DataClient
{
    private static final Logger log = LogManager.getLogger("DataClient");

    private String address;
    private int port;

    private Socket socket;
    private BufferedReader reader;

    private Thread readThread;

    private Gson gson;

    private int nextId;
    private TIntObjectMap<DataHandler> handlers;

    public DataClient(String address, int port)
    {
        this.address = address;
        this.port = port;

        this.gson = new Gson();

        this.nextId = 0;
        this.handlers = new TIntObjectHashMap<>();
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

        this.readThread.start();
    }

    public DataHandler push(String request, String token) throws IOException
    {
        return push(request, token, null);
    }

    public DataHandler push(String request) throws IOException
    {
        return push(request, null, null);
    }

    public DataHandler push(String request, Object param) throws IOException
    {
        return push(request, null, param);
    }

    public DataHandler push(String request, String token, Object params) throws IOException
    {
        JsonObject object = new JsonObject();

        int id = nextId();
        object.addProperty("id", id);

        if (token != null)
        {
            object.addProperty("token", token);
        }

        object.addProperty("request", request);
        object.add("params", gson.toJsonTree(params));

        String result = gson.toJson(object) + "\n";

        System.out.print(" --> " + result);
        this.socket.getOutputStream().write(result.getBytes());

        DataHandler handler = new DataHandler();
        this.handlers.put(id, handler);

        return handler;
    }

    protected synchronized int nextId()
    {
        return nextId++;
    }

    protected void handle(String line)
    {
        System.out.println(" <-- " + line);

        Response response = gson.fromJson(line, Response.class);
        DataHandler handler = this.handlers.get(response.getId());

        if (handler == null)
        {
            log.warn("Received response for unknown request id '" + response.getId() + "', ignoring");
            return;
        }

        if (handler.getHandler() == null)
        {
            log.warn("No handler for request id '" + response.getId());
            return;
        }

        handler.getHandler().accept(response);
    }

    public class DataHandler
    {
        private Consumer<Response> handler;

        public void handle(Consumer<Response> handler)
        {
            this.handler = handler;
        }

        protected Consumer<Response> getHandler()
        {
            return handler;
        }
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
