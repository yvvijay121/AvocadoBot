package AvocadoBot.commands.moderation;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import AvocadoBot.CustomEmbedBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class TempMuteCommand implements MessageCreateListener {

    private static String prefix;

    public TempMuteCommand(String pre) {
        prefix = pre;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith(prefix + "tempmute")) {
            User userToMute = event.getMessage().getMentionedUsers().get(0);
            Role r = event.getServer().get().getRolesByNameIgnoreCase("muted").get(0);
            String[] reasonList = event.getMessageContent().split(" ", 4);
            if (reasonList.length > 2) {
                boolean a = event.getMessage().getAuthor().canManageRolesOnServer();
                String reason;
                if (reasonList.length < 4) {
                    reason = "Unspecified";
                } else {
                    reason = reasonList[3];
                }
                if (a) {
                    userToMute.addRole(r);
                    int duration;
                    try {
                        duration = Integer.parseInt(reasonList[2]);
                    } catch (Exception e) {
                        duration = 1;
                    }
                    event.getApi().getThreadPool().getScheduler().schedule(() -> userToMute.removeRole(r), duration, TimeUnit.SECONDS);
                    EmbedBuilder embed = new CustomEmbedBuilder()
                            .setTitle("Successfully Muted User")
                            .setDescription(userToMute.getDiscriminatedName() + " was muted.\nDuration: " + reasonList[2] + " seconds(s)\nReason: " + reason)
                            .setColor(new Color(204, 44, 44));
                    event.getChannel().sendMessage(embed);
                } else {
                    EmbedBuilder embed = new CustomEmbedBuilder()
                            .setTitle("You don't have permissions")
                            .setDescription("You don't have permissions to mute this user.")
                            .setColor(new Color(204, 44, 44));
                    event.getChannel().sendMessage(embed);
                }
            } else {
                EmbedBuilder embed = new CustomEmbedBuilder()
                        .setTitle("Incorrect Usage")
                        .setDescription("Usage: `" + prefix + "tempmute [user] [duration in seconds] {reason}`")
                        .setColor(new Color(204, 44, 44));
                event.getChannel().sendMessage(embed);
            }
        }
    }
}
