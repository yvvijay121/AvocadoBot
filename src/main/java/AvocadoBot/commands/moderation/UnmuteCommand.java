package AvocadoBot.commands.moderation;

import java.awt.Color;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class UnmuteCommand implements MessageCreateListener {

    private static String prefix;

    public UnmuteCommand(String pre) {
        prefix = pre;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith(prefix + "unmute")) {
            User userToUnmute = event.getMessage().getMentionedUsers().get(0);
            Role r = event.getServer().get().getRolesByNameIgnoreCase("Muted").get(0);
            boolean a = event.getMessage().getAuthor().canManageRolesOnServer();
            if (a) {
                userToUnmute.removeRole(r);
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Successfully Unmuted User")
                        .setDescription(userToUnmute.getDiscriminatedName() + " was unmuted.")
                        .setColor(new Color(204, 44, 44));
                event.getChannel().sendMessage(embed);
            } else {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("You don't have permissions")
                        .setDescription("You don't have permissions to unmute this user.")
                        .setColor(new Color(204, 44, 44));
                event.getChannel().sendMessage(embed);
            }
        }
    }
}
