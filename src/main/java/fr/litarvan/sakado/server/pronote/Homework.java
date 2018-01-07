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
package fr.litarvan.sakado.server.pronote;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

@JsonDeserialize(using = Homework.HomeworkDeserializer.class)
public class Homework
{
    private String subject;
    private String content;
    private Calendar since;
    private Calendar until;

    Homework(String subject, String content, Calendar since, Calendar until)
    {
        this.subject = subject;
        this.content = content;
        this.since = since;
        this.until = until;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getContent()
    {
        return content;
    }

    public Calendar getSince()
    {
        return since;
    }

    public Calendar getUntil()
    {
        return until;
    }

    public static class HomeworkDeserializer extends StdDeserializer<Homework>
    {
        protected HomeworkDeserializer()
        {
            super(Homework.class);
        }

        @Override
        public Homework deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException
        {
            JsonNode node = p.getCodec().readTree(p);

            JsonNode until = node.get("day");
            Calendar day = new GregorianCalendar();

            day.set(Calendar.DAY_OF_MONTH, until.get("day").asInt());
            day.set(Calendar.MONTH, until.get("month").asInt());

            String[] sinceSplit = node.get("since").asText().split("/");
            Calendar since = new GregorianCalendar();

            since.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sinceSplit[0]));
            since.set(Calendar.MONTH, Integer.parseInt(sinceSplit[1]));

            return new Homework(node.get("subject").asText().trim(), node.get("content").asText().trim(), since, day);
        }
    }
}
