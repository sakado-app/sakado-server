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
package fr.litarvan.sakado.server.push;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.litarvan.commons.config.ConfigProvider;
import fr.litarvan.sakado.server.data.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PushService
{
    public static final String FCM_SEND_URL = "https://fcm.googleapis.com/fcm/send";

    private static final Logger log = LogManager.getLogger("PushService");

    @Inject
    private ConfigProvider config;

    @Inject
    private Gson gson;

    public void send(User user, String message) throws IOException
    {
        send(user, null, "Sakado", message, null, null);
    }

    public void send(User user, String title, String message) throws IOException
    {
        send(user, null, title, message, null, null);
    }

    public void send(User user, PushType type, String message) throws IOException
    {
        send(user, type, "Sakado", message);
    }

    public void send(User user, PushType type, String title, String message) throws IOException
    {
        send(user, type.name(), title, message, type.getColor(), type.getIcon());
    }

    public void send(User user, String type, String title, String message, String color, String icon) throws IOException
    {
        log.info("Sending '" + type + "' push notification '" + title + "' : '" + message + "' to '" + user.getUsername() + "'");

        if (user.getDeviceToken() == null)
        {
            return;
        }

        String key = config.at("fcm.server-key");

        if ("YOUR_SERVER_KEY".equals(key))
        {
            return;
        }

        URL url = new URL(FCM_SEND_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "key=" + key);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        JsonObject data = new JsonObject();
        data.addProperty("title", title);
        data.addProperty("message", message);

        if (color != null)
        {
            data.addProperty("color", color);
        }

        if (icon != null)
        {
            data.addProperty("icon", icon);
        }

        if (type != null)
        {
            data.addProperty("type", type);
        }

        JsonObject request = new JsonObject();
        request.addProperty("to", user.getDeviceToken());
        request.add("data", data);

        String requestContent = gson.toJson(request);

        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(requestContent.getBytes());

        if (conn.getResponseCode() != 200)
        {
            throw new IllegalStateException("Firebase error : [" + conn.getResponseCode() + "] " + conn.getResponseMessage());
        }
    }
}
