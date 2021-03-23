package AvocadoBot.commands.utility;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.activity.Activity;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.util.logging.ExceptionLogger;

import java.awt.*;
import java.util.NoSuchElementException;

public class UserInfoCommand implements CommandExecutor {

    private String surroundFormatting(String s) {
        return "```" + s + "```";
    }

    @Command(aliases = {"userinfo", "uinfo"}, description = "Gets information about the mentioned user, or the user who called the command if no user is mentioned.", usage = "userinfo [<user mention>]", privateMessages = false)
    public void onMessageCreate(Message m, Server s, TextChannel tc) {
        try {
            User u;
            if (m.getMentionedUsers().size() != 0) {
                u = m.getMentionedUsers().get(0);
            } else {
                u = m.getAuthor().asUser().orElseThrow();
            }
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("User Info")
                    .addField("Display Name", surroundFormatting(u.getDisplayName(s)), true)
                    .addField("Name + Discriminator", surroundFormatting(u.getDiscriminatedName()), true)
                    .addField("User Id", surroundFormatting(u.getIdAsString()), true)
                    .setColor(new Color(204, 44, 44))
                    .setAuthor(u);
            // Keep in mind that a message author can either be a webhook or a normal user
            embed.addField("Online Status", surroundFormatting(u.getStatus().getStatusString()), true);
            embed.addField("Connected Clients", surroundFormatting(u.getCurrentClients().toString()), true);
            // The User#getActivity() method returns an Optional
            embed.addField("Activity", surroundFormatting(u.getActivity().map(Activity::getName).orElse("None")), true);
            try {
                embed.addField("Custom Status", surroundFormatting(u.getActivity().map(Activity::getState).orElseThrow().orElse("No Custom Status")), true);
            } catch (NoSuchElementException e) {
                embed.addField("Custom Status", "```No Custom Status```", true);
            }
            // Keep in mind that messages can also be sent as private messages
            embed.addField("Server Admin", s.isAdmin(u) ? "```yes```" : "```no```", true);
            // Send the embed. It logs every exception, besides missing permissions (you are not allowed to send message in the channel)
            tc.sendMessage(embed)
                    .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
        } catch (Exception e) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Something went wrong.")
                    .setDescription("Try it again. If the error persists, sorry.");
            tc.sendMessage(embed);
        }
    }
}

