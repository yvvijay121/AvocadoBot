package AvocadoBot.commands.moderation;

import java.awt.Color;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class KickCommand implements MessageCreateListener {

    private static String prefix;

    public KickCommand(String pre) {
        prefix = pre;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith(prefix + "kick")) {
            User userToKick = event.getMessage().getMentionedUsers().get(0);
            String[] reasonList = event.getMessageContent().split(" ", 3);
            boolean a = event.getMessage().getAuthor().canKickUserFromServer(userToKick);
            try {
                if (!(event.getMessageAuthor().getDiscriminatedName().equals(userToKick.getDiscriminatedName()))) {
                    if (a) {
                        if(reasonList.length > 2){
                            event.getMessage().getServer().get().kickUser(userToKick, reasonList[2]);
                        }else{
                            event.getMessage().getServer().get().kickUser(userToKick);
                        }
                        EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Successfully Kicked User")
                            .setDescription(userToKick.getName() + "#" + userToKick.getDiscriminator() + " was kicked.\nReason: " + reasonList[2])
                            .setColor(new Color(204, 44, 44));
                        event.getChannel().sendMessage(embed);
                    } else {
                        EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("You don't have permissions")
                            .setDescription("You don't have permissions to kick this user.")
                            .setColor(new Color(204, 44, 44));
                        event.getChannel().sendMessage(embed);
                    }
                } else {
                    EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("You're trying to kick yourself.")
                        .setColor(new Color(204, 44, 44));
                    event.getChannel().sendMessage(embed);
                }
            } catch (Exception e) {
                EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Something happened.")
                    .setDescription("The bot probably doesn't have permissions to kick this user, or the bot failed to kick the user.")
                    .setColor(new Color(204, 44, 44));
                event.getChannel().sendMessage(embed);
            }
        }
    }

}
