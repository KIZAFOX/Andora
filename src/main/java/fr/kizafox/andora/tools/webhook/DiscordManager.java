package fr.kizafox.andora.tools.webhook;

import fr.kizafox.andora.Andora;

import java.awt.*;
import java.io.IOException;

public class DiscordManager {

    private final Andora instance;

    public DiscordManager(Andora instance) {
        this.instance = instance;
    }

    public void execute(String message) {
        try {
            DiscordWebhook discordWebhook = new DiscordWebhook("https://discord.com/api/webhooks/993959791472955392/ryOhF9EPfjnHDG6zjXO7WTWjQwetXCzA--Egy-Kbl68n8f9rsmhXgu31y9j6M50nDzOk");
            String ICON_URL = "https://i.ibb.co/x2BKCfw/server-icon.png";
            discordWebhook.setAvatarUrl(ICON_URL);
            discordWebhook.setUsername("Andora" + this.instance.getDescription().getVersion());
            discordWebhook.setTts(false);
            discordWebhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setAuthor("Andora", this.instance.getDescription().getWebsite(), ICON_URL)
                    .setDescription(message)
                    .setColor(Color.RED)
                    .setFooter("Andora plugin fait par KIZAFOX", ICON_URL)
                    .setUrl(this.instance.getDescription().getWebsite()));
            discordWebhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}