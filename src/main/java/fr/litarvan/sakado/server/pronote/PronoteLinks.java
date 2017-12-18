package fr.litarvan.sakado.server.pronote;

import fr.litarvan.commons.config.ConfigProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PronoteLinks
{
    private PronoteLink[] links;

    @Inject
    public PronoteLinks(ConfigProvider config)
    {
        this.links = config.at("pronote.links", PronoteLink[].class);
    }

    public PronoteLink[] all()
    {
        return links;
    }

    public static class PronoteLink
    {
        private String name;
        private String link;

        public PronoteLink(String name, String link)
        {
            this.name = name;
            this.link = link;
        }

        public String getName()
        {
            return name;
        }

        public String getLink()
        {
            return link;
        }
    }
}
