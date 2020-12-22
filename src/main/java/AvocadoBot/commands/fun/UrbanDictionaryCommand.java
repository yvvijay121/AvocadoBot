package AvocadoBot.commands.fun;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class UrbanDictionaryCommand implements MessageCreateListener {

    private static String prefix;

    public UrbanDictionaryCommand(String pre) {
        prefix = pre;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith(prefix + "udict")) {
            if (event.getMessageContent().split(" ").length == 1) {
                try {
                    Document doc = Jsoup.connect("https://www.urbandictionary.com/random.php?page=" + Integer.toString((int) Math.round(Math.random() * 1000))).get();
                    Element content = doc.getElementsByClass("def-panel").get(0);
                    String theWord = content.getElementsByClass("def-header").get(0).child(0).html();
                    String theDefinition = Jsoup.parse(content.getElementsByClass("meaning").get(0).html()).text();
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle(theWord)
                            .setDescription(theDefinition)
                            .setColor(new Color(204, 44, 44))
                            .setAuthor("Random Urban Dictionary Word", "https://www.urbandictionary.com/", "");
                    event.getChannel().sendMessage(embed).exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
                } catch (HttpStatusException ex) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("404 Error")
                            .setDescription("Urban Dictionary doesn't have an entry for that.")
                            .setColor(new Color(204, 44, 44))
                            .setAuthor("Random Urban Dictionary Word", "https://www.urbandictionary.com/", "");
                    event.getChannel().sendMessage(embed).exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
                } catch (IOException ex) {
                    Logger.getLogger(UrbanDictionaryCommand.class.getName()).log(Level.SEVERE, null, ex);
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("IOException Error")
                            .setDescription("The bot couldn't connect to the server.")
                            .setColor(new Color(204, 44, 44))
                            .setAuthor("Random Urban Dictionary Word", "https://www.urbandictionary.com/", "");
                    event.getChannel().sendMessage(embed).exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
                }
            } else {
                String restOfThing = event.getMessageContent().split(" ", 2)[1];
                try {
                    Document doc = Jsoup.connect("https://www.urbandictionary.com/define.php?term=" + restOfThing).get();
                    Element content = doc.getElementsByClass("def-panel").get(0);
                    String theWord = content.getElementsByClass("def-header").get(0).child(0).html();
                    String theDefinition = Jsoup.parse(content.getElementsByClass("meaning").get(0).html()).text();
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle(theWord)
                            .setDescription(theDefinition)
                            .setColor(new Color(204, 44, 44))
                            .setAuthor("Random Urban Dictionary Word", "https://www.urbandictionary.com/", "");
                    event.getChannel().sendMessage(embed).exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
                } catch (HttpStatusException ex) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("404 Error")
                            .setDescription("Urban Dictionary doesn't have an entry for that.")
                            .setColor(new Color(204, 44, 44))
                            .setAuthor("Random Urban Dictionary Word", "https://www.urbandictionary.com/", "");
                    event.getChannel().sendMessage(embed).exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
                } catch (IOException ex) {
                    Logger.getLogger(UrbanDictionaryCommand.class.getName()).log(Level.SEVERE, null, ex);
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("IOException Error")
                            .setDescription("The bot couldn't connect to the server.")
                            .setColor(new Color(204, 44, 44))
                            .setAuthor("Random Urban Dictionary Word", "https://www.urbandictionary.com/", "");
                    event.getChannel().sendMessage(embed).exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
                }
            }
        }
    }

}
