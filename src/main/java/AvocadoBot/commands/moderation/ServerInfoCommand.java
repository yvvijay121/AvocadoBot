package AvocadoBot.commands.moderation;

import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

import java.awt.*;

public class ServerInfoCommand implements MessageCreateListener {
    private static String prefix;

    public ServerInfoCommand(String p){ prefix = p; }

    private String sf(String a){ return "```" + a + "```"; }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equalsIgnoreCase(prefix + "serverinfo")) {
            Server server = event.getServer().orElse(null);
            if(server != null){
                String serverName = server.getName();
                int numberOfTotalChannels = server.getChannels().size();
                int numberOfTextChannels = server.getTextChannels().size();
                int numberOfVoiceChannels = server.getVoiceChannels().size();
                int numberOfCategories = server.getChannelCategories().size();
                StringBuilder channelsummary = new StringBuilder();
                channelsummary.append(numberOfTotalChannels);
                channelsummary.append(" Channels, ");
                channelsummary.append(numberOfCategories);
                channelsummary.append(" Categories\nText Channels: ");
                channelsummary.append(numberOfTextChannels);
                channelsummary.append("\nVoice Channels: ");
                channelsummary.append(numberOfVoiceChannels);
                String serverID = server.getIdAsString();
                User serverOwner = server.getOwner().orElse(null);
                String serverOwnerDiscriminator = "None";
                if(serverOwner != null) serverOwnerDiscriminator = serverOwner.getDiscriminatedName();
                Icon serverIcon = server.getIcon().orElse(null);
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Server Info");
                embed.setThumbnail(serverIcon);
                embed.addField("Name:", sf(serverName), true);
                embed.addField("ID", sf(serverID), true);
                embed.addField("Channel Count", sf(channelsummary.toString()), false);
                String roleAmount = server.getRoles().size() + " Roles";
                embed.addField("Roles", sf(roleAmount), true);
                String memberString = server.getMemberCount() + " Members";
                embed.addField("Members", sf(memberString), true);
                embed.addField("Owner", sf(serverOwnerDiscriminator), true);
                embed.addField("Requested By", sf(event.getMessageAuthor().getDiscriminatedName()), true);
                embed.setColor(new Color(204, 44, 44));
                event.getChannel().sendMessage(embed)
                    .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            }
        }
    }
}
