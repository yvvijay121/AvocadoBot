package AvocadoBot.commands.moderation;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.util.Arrays;

public class BanCommand implements CommandExecutor {
    @Command(aliases = {"ban"}, description = "Bans the mentioned user.", usage = "ban <user mention> [<reason for kick>]")
    public void onMessageCreate(String[] args, Message m, Server s, TextChannel tc) {
        User userToBan = m.getMentionedUsers().get(0);
        String reasonList = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
        System.out.println(reasonList);
        boolean a = m.getAuthor().canBanUserFromServer(userToBan);
        try {
            if (!(m.getAuthor().getDiscriminatedName().equals(userToBan.getDiscriminatedName()))) {
                if (a) {
                    if (reasonList.equals("")) {
                        s.banUser(userToBan, 1, reasonList);
                    } else {
                        s.banUser(userToBan);
                    }
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Successfully Banned User")
                            .setDescription(userToBan.getDiscriminatedName() + " was banned.\nReason: " + reasonList)
                            .setColor(new Color(204, 44, 44));
                    tc.sendMessage(embed);
                } else {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("You don't have permissions")
                            .setDescription("You don't have permissions to ban this user.")
                            .setColor(new Color(204, 44, 44));
                    tc.sendMessage(embed);
                }
            } else {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("You're trying to ban yourself.")
                        .setColor(new Color(204, 44, 44));
                tc.sendMessage(embed);
            }
        } catch (Exception e) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Something happened.")
                    .setDescription("The bot probably doesn't have permissions to ban this user, or the bot failed to ban the user.")
                    .setColor(new Color(204, 44, 44));
            tc.sendMessage(embed);
        }
    }
}


