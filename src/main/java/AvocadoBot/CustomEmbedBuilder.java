package AvocadoBot;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class CustomEmbedBuilder extends EmbedBuilder {
    public CustomEmbedBuilder() {
        super();
        super.setAuthor("AvocadoBot", null, "https://cdn.discordapp.com/app-icons/788047565714882621/19ecfba38a0abf3ebd385f117168d3bb.png?size=512");
        super.setColor(new Color(204, 44, 44));
        super.setFooter("This bot was made by kickit123#9962.");
    }
}
