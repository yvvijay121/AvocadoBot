package AvocadoBot.commands.moderation;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class UnmuteCommand implements CommandExecutor {
    @Command(aliases = {"unmute"}, description = "Unmutes the mentioned user.", usage = "unmute <user mention>")
    public void onMessageCreate(Message m, Server s, TextChannel tc) {
        User userToUnmute = m.getMentionedUsers().get(0);
        Role r = s.getRolesByNameIgnoreCase("muted").get(0);

        if (m.getAuthor().canManageRolesOnServer()) {
            userToUnmute.removeRole(r);
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Successfully Unmuted User")
                    .setDescription(userToUnmute.getDiscriminatedName() + " was unmuted.")
                    .setColor(new Color(204, 44, 44));
            tc.sendMessage(embed);
        } else {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("You don't have permissions")
                    .setDescription("You don't have permissions to unmute this user.")
                    .setColor(new Color(204, 44, 44));
            tc.sendMessage(embed);
        }
    }
}

