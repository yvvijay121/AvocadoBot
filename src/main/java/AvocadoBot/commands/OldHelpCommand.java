package AvocadoBot.commands;

import java.awt.Color;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

public class HelpCommand implements MessageCreateListener {
    private static String prefix;
    
    public HelpCommand(String pre){
        prefix = pre;
    }
    
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equalsIgnoreCase(prefix + "help")) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Help Menu")
                    .setDescription("This is a work in progress, mention the developer for help.")
                    .addInlineField("`" + prefix + "ping" + "`", "Testing command.")
                    .addInlineField("`" + prefix + "kick" + "`", "Kicks the mentioned user.")
                    .addInlineField("`" + prefix + "ban" + "`", "Bans the mentioned user.")
                    .addInlineField("`" + prefix + "unban" + "`", "Unbans the mentioned user.")
                    .addInlineField("`" + prefix + "mute" + "`", "Mutes the mentioned user.")
                    .addInlineField("`" + prefix + "tempmute" + "`", "Temporarily mutes the mentioned user for a specified number of seconds.")
                    .addInlineField("`" + prefix + "unmute" + "`", "Unmutes the mentioned user.")
                    .addInlineField("`" + prefix + "clear" + "`", "Clears the specified number of messages in a text channel.")
                    .addInlineField("`" + prefix + "userinfo" + "`", "Gets the user info of the mentioned user, or the author if user not specified.")
                    .addInlineField("`" + prefix + "udict" + "`", "Gets the definition of a specified work from Urban Dictionary, or a random word if no search specified.")
                    .addInlineField("`" + prefix + "xkcd" + "`", "Gets a random comic from XKCD.")
                    .addInlineField("`" + prefix + "joke" + "`", "Gets a random joke from r/jokes.")
                    .setColor(new Color(204,44,44));
            event.getChannel().sendMessage(embed).exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
        }
    }

}
