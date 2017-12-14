package fr.litarvan.sakado.server.pronote.network;

import com.googlecode.jsonrpc4j.JsonRpcClient;

import java.io.IOException;
import java.net.Socket;

public class NetworkClient
{
    private Socket socket;
    private JsonRpcClient client;

    public NetworkClient(String address, int port) throws IOException
    {
        this.socket = new Socket(address, port);
        this.client = new JsonRpcClient();
    }

    public void push(String request, Object params) throws IOException, RequestException
    {
        push(request, params, Object.class);
    }

    public <T> T push(String request, Class<T> returnType) throws IOException, RequestException
    {
        return push(request, null, returnType);
    }

    public <T> T push(String request, Object params, Class<T> returnType) throws IOException, RequestException
    {
        try
        {
            return client.invokeAndReadResponse(request, params, returnType, socket.getOutputStream(), socket.getInputStream());
        }
        catch (IOException e)
        {
            throw e;
        }
        catch (Throwable t)
        {
            throw new RequestException(t);
        }
    }
}
