package AvocadoBot.commands.moderation;
//FIX PERMISSIONS SECTION
import java.awt.Color;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class UnbanCommand implements MessageCreateListener {
    private static String prefix;
    
    public UnbanCommand(String pre){
        prefix = pre;
    }
    
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith(prefix + "unban")) {
            String[] discrimList = event.getMessageContent().split(" ");
            try{
                String discrim = discrimList[1];
                User userToUnban = event.getMessage().getMentionedUsers().get(0);
                User u = event.getServer().get().getMemberByDiscriminatedName(discrim).get();
                if (!(event.getMessageAuthor().getDiscriminatedName().equals(userToUnban.getDiscriminatedName()))){
                    event.getMessage().getServer().get().unbanUser(userToUnban);
                    EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Successfully Banned User")
                        .setDescription(userToUnban.getDiscriminatedName() + " was unbanned.")
                        .setColor(new Color(204, 44, 44));
                    event.getChannel().sendMessage(embed);
                } else {
                    EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("You're trying to unban yourself.")
                        .setColor(new Color(204, 44, 44));
                    event.getChannel().sendMessage(embed);
                }
            }catch(Exception e){
                EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Something happened.")
                    .setDescription("The bot couldn't unban the person correctly."
                            + "\nThis may be an intents error, or you're not in any servers with the banned user.")
                    .setColor(new Color(204, 44, 44));
                event.getChannel().sendMessage(embed);
            }
        }
    }
}