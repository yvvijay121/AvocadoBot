package AvocadoBot.commands.utility;

import AvocadoBot.CustomEmbedBuilder;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.util.logging.ExceptionLogger;

import java.awt.*;

public class ServerInfoCommand implements CommandExecutor {
    private String sf(String s) {
        return ("```" + s + "```");
    }

    @Command(aliases = {"serverinfo", "sinfo"}, description = "Get information about the current server.", usage = "serverinfo", privateMessages = false)
    public void onMessageCreate(Server s, Message m, TextChannel tc) {
        if (s != null) {
            String serverName = s.getName();
            int numberOfTotalChannels = s.getChannels().size();
            int numberOfTextChannels = s.getTextChannels().size();
            int numberOfVoiceChannels = s.getVoiceChannels().size();
            int numberOfCategories = s.getChannelCategories().size();
            String serverID = s.getIdAsString();
            User serverOwner = s.getOwner().orElse(null);
            String serverOwnerDiscriminator = "None";
            if (serverOwner != null) serverOwnerDiscriminator = serverOwner.getDiscriminatedName();
            Icon serverIcon = s.getIcon().orElse(null);
            EmbedBuilder embed = new CustomEmbedBuilder();
            embed.setTitle("Server Info");
            embed.setThumbnail(serverIcon);
            embed.addField("Name:", sf(serverName), true);
            embed.addField("ID", sf(serverID), true);
            String channelsummary = numberOfTotalChannels +
                    " Channels, " +
                    numberOfCategories +
                    " Categories\nText Channels: " +
                    numberOfTextChannels +
                    "\nVoice Channels: " +
                    numberOfVoiceChannels;
            embed.addField("Channel Count", sf(channelsummary), false);
            String roleAmount = s.getRoles().size() + " Roles";
            embed.addField("Roles", sf(roleAmount), true);
            String memberString = s.getMemberCount() + " Members";
            embed.addField("Members", sf(memberString), true);
            embed.addField("Owner", sf(serverOwnerDiscriminator), true);
            embed.addField("Requested By", sf(m.getAuthor().getDiscriminatedName()), true);
            embed.setColor(new Color(204, 44, 44));
            tc.sendMessage(embed)
                    .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
        }
    }
}

