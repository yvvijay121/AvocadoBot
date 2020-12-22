package AvocadoBot.commands.moderation;

import java.awt.Color;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class BanCommand implements MessageCreateListener {

    private static String prefix;

    public BanCommand(String pre) {
        prefix = pre;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith(prefix + "ban")) {
            User userToBan = event.getMessage().getMentionedUsers().get(0);
            String[] reasonList = event.getMessageContent().split(" ", 3);
            boolean a = event.getMessage().getAuthor().canBanUserFromServer(userToBan);
            try {
                if (!(event.getMessageAuthor().getDiscriminatedName().equals(userToBan.getDiscriminatedName()))) {
                    if (a) {
                        if(reasonList.length > 2){
                            event.getMessage().getServer().get().banUser(userToBan, 1, reasonList[2]);
                        }else{
                            event.getMessage().getServer().get().banUser(userToBan);
                        }
                        EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Successfully Banned User")
                            .setDescription(userToBan.getDiscriminatedName() + " was banned.\nReason: " + reasonList[2])
                            .setColor(new Color(204, 44, 44));
                        event.getChannel().sendMessage(embed);
                    } else {
                        EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("You don't have permissions")
                            .setDescription("You don't have permissions to ban this user.")
                            .setColor(new Color(204, 44, 44));
                        event.getChannel().sendMessage(embed);
                    }
                } else {
                    EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("You're trying to ban yourself.")
                        .setColor(new Color(204, 44, 44));
                    event.getChannel().sendMessage(embed);
                }
            } catch (Exception e) {
                EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Something happened.")
                    .setDescription("The bot probably doesn't have permissions to ban this user, or the bot failed to ban the user.")
                    .setColor(new Color(204, 44, 44));
                event.getChannel().sendMessage(embed);
            }
        }
    }

}
