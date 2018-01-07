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
