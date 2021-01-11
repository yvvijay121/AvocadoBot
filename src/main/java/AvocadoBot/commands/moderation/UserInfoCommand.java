package AvocadoBot.commands.moderation;

import org.javacord.api.entity.activity.Activity;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

import java.awt.*;
import java.util.NoSuchElementException;

public class UserInfoCommand implements MessageCreateListener {
    private static String prefix;

    public UserInfoCommand(String pre) {
        prefix = pre;
    }

    private String surroundFormatting(String a) {
        return "```" + a + "```";
    }

    /*
     * This command can be used to display information about the user who used the command.
     * It's a good example for the MessageAuthor, MessageBuilder and ExceptionLogger class.
     */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith(prefix + "userinfo")) {
            try {
                User u;
                if (event.getMessage().getMentionedUsers().size() != 0) {
                    u = event.getMessage().getMentionedUsers().get(0);
                } else {
                    u = event.getMessageAuthor().asUser().orElseThrow();
                }
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("User Info")
                        .addField("Display Name", surroundFormatting(u.getDisplayName(event.getServer().get())), true)
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
                event.getMessage().getServer()
                        .ifPresent(server -> embed.addField("Server Admin", server.isAdmin(u) ? "```yes```" : "```no```", true));
                // Send the embed. It logs every exception, besides missing permissions (you are not allowed to send message in the channel)
                event.getChannel().sendMessage(embed)
                        .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            } catch (Exception e) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Something went wrong.")
                        .setDescription("Try it again. If the error persists, sorry.");
                event.getChannel().sendMessage(embed);
            }
        }
    }

}