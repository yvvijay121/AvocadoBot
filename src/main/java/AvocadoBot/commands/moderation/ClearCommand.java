package AvocadoBot.commands.moderation;

import AvocadoBot.CustomEmbedBuilder;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class ClearCommand implements CommandExecutor {

    @Command(aliases = {"clear", "purge"}, description = "Clears the number of messages declared from the channel.", usage = "clear <#>")
    public void onMessageCreate(Message m, TextChannel channel, String[] args) {
        int messagesDeleted;
        try {
            messagesDeleted = Integer.parseInt(args[0]) + 1;
            channel.getMessages(messagesDeleted).get().deleteAll();
            EmbedBuilder embed = new CustomEmbedBuilder()
                    .setTitle("Cleared Messages")
                    .setDescription(args[0] + " messages have been deleted from this text channel."
                            + "\nRequested by: " + m.getAuthor().getDiscriminatedName())
                    .setColor(new Color(204, 44, 44));
            channel.sendMessage(embed);
        } catch (Exception ex) {
            ex.printStackTrace();
            EmbedBuilder embed = new CustomEmbedBuilder()
                    .setTitle("Error").setDescription("Something went wrong.");
            channel.sendMessage(embed);
        }
    }
}


