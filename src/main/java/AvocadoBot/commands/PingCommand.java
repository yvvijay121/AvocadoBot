package AvocadoBot.commands;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

public class PingCommand implements MessageCreateListener {
    private static String prefix;
    
    public PingCommand(String pre){
        prefix = pre;
    }
    
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equalsIgnoreCase(prefix + "ping")) {
            event.getChannel().sendMessage("Who pinged me?").exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
        }
    }
}