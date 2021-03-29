package AvocadoBot.commands.moderation;

import AvocadoBot.CustomEmbedBuilder;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.util.Arrays;

public class MuteCommand implements CommandExecutor {
    @Command(aliases = {"mute", "silence"}, description = "Mutes the mentioned user.", usage = "mute <user mention> [<reason for kick>]")
    public void onMessageCreate(String[] args, Server s, Message m, TextChannel tc) {
        User userToMute = m.getMentionedUsers().get(0);
        Role r = s.getRolesByNameIgnoreCase("muted").get(0);
        String reason = String.join(" ", Arrays.copyOfRange(args, 3, args.length));
        if (reason.equals("")) {
            reason = "Unspecified";
        }
        if (m.getAuthor().canManageRolesOnServer()) {
            userToMute.addRole(r);
            EmbedBuilder embed = new CustomEmbedBuilder()
                    .setTitle("Successfully Muted User")
                    .setDescription(userToMute.getDiscriminatedName() + " was muted.\nReason: " + reason)
                    .setColor(new Color(204, 44, 44));
            tc.sendMessage(embed);
        } else {
            EmbedBuilder embed = new CustomEmbedBuilder()
                    .setTitle("You don't have permissions")
                    .setDescription("You don't have permissions to mute this user.")
                    .setColor(new Color(204, 44, 44));
            tc.sendMessage(embed);
        }
    }
}

