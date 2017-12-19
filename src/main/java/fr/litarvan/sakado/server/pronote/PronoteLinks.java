package fr.litarvan.sakado.server.pronote;

import fr.litarvan.commons.config.ConfigProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PronoteLinks
{
    private ConfigProvider config;
    private PronoteLink[] links;

    @Inject
    public PronoteLinks(ConfigProvider config)
    {
        this.config = config;
    }

    public PronoteLink[] all()
    {
        return config.at("pronote.links", PronoteLink[].class);
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
