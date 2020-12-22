package AvocadoBot.commands.moderation;

import java.awt.Color;
import java.util.concurrent.ExecutionException;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class ClearCommand implements MessageCreateListener {
    private static String prefix;
    
    public ClearCommand(String pre){
        prefix = pre;
    }
    
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String[] reasonList = event.getMessageContent().split(" ");
        if (event.getMessageContent().startsWith(prefix + "clear")) {
            int messagesDeleted;
            try{
                messagesDeleted = Integer.parseInt(reasonList[1]);
                event.getChannel().getMessages(messagesDeleted).get().deleteAll();
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Cleared Messages")
                        .setDescription(reasonList[1] + " messages have been deleted from this text channel."
                                + "\nRequested by: " + event.getMessage().getAuthor().getDiscriminatedName())
                        .setColor(new Color(204, 44, 44));
                event.getChannel().sendMessage(embed);
            }catch(InterruptedException | NumberFormatException | ExecutionException e){
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Incorrect Usage")
                        .setDescription("Usage: `" + prefix + "clear [# of messages] {reason}`")
                        .setColor(new Color(204, 44, 44));
                event.getChannel().sendMessage(embed);
            }
        }
    }
}
