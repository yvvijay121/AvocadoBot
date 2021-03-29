package AvocadoBot.commands.moderation;
//FIX PERMISSIONS SECTION

import AvocadoBot.CustomEmbedBuilder;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.awt.*;

public class UnbanCommand implements CommandExecutor {
    @Command(aliases = {"unban"}, description = "Unbans the mentioned user.", usage = "unban <user mention>")
    public void onMessageCreate(Message m, Server s, TextChannel tc) {
        try {
            User userToUnban = m.getMentionedUsers().get(0);
            if (!(m.getAuthor().getDiscriminatedName().equals(userToUnban.getDiscriminatedName()))) {
                s.unbanUser(userToUnban);
                EmbedBuilder embed = new CustomEmbedBuilder()
                        .setTitle("Successfully Banned User")
                        .setDescription(userToUnban.getDiscriminatedName() + " was unbanned.")
                        .setColor(new Color(204, 44, 44));
                tc.sendMessage(embed);
            } else {
                EmbedBuilder embed = new CustomEmbedBuilder()
                        .setTitle("You're trying to unban yourself.")
                        .setColor(new Color(204, 44, 44));
                tc.sendMessage(embed);
            }
        } catch (Exception e) {
            EmbedBuilder embed = new CustomEmbedBuilder()
                    .setTitle("Something happened.")
                    .setDescription("The bot couldn't unban the person correctly."
                            + "\nThis may be an intents error, or you're not in any servers with the banned user.")
                    .setColor(new Color(204, 44, 44));
            tc.sendMessage(embed);
        }
    }
}
