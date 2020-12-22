package AvocadoBot.commands.moderation;

import java.awt.Color;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class MuteCommand implements MessageCreateListener {

    private static String prefix;

    public MuteCommand(String pre) {
        prefix = pre;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith(prefix + "mute")) {
            User userToMute = event.getMessage().getMentionedUsers().get(0);
            Role r = event.getServer().get().getRolesByNameIgnoreCase("Muted").get(0);
            String[] reasonList = event.getMessageContent().split(" ", 3);
            boolean a = event.getMessage().getAuthor().canManageRolesOnServer();
            String reason;
            if(reasonList.length < 3){
                reason = "Unspecified";
            }else{
                reason = reasonList[2];
            }
            if (a) {
                userToMute.addRole(r);
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Successfully Muted User")
                        .setDescription(userToMute.getDiscriminatedName() + " was muted.\nReason: " + reason)
                        .setColor(new Color(204, 44, 44));
                event.getChannel().sendMessage(embed);
            } else {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("You don't have permissions")
                        .setDescription("You don't have permissions to mute this user.")
                        .setColor(new Color(204, 44, 44));
                event.getChannel().sendMessage(embed);
            }
        }
    }
}
