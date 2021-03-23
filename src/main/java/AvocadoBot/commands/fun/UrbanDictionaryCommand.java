package AvocadoBot.commands.fun;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.util.logging.ExceptionLogger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UrbanDictionaryCommand implements CommandExecutor {

    @Command(aliases = {"udict", "ud", "randomword"},
            description = "Retrieves either a random work from Urban Dictionary," +
                    "or search up a word on Urban Dictionary.",
            usage = "udict [<search>]")
    public void onMessageCreate(TextChannel channel, String[] args) {
        System.out.println(Arrays.toString(args));
        if (args.length < 1) {
            try {
                Document doc = Jsoup.connect(("https://www.urbandictionary.com/random.php?page=" + (int) Math.round(Math.random() * 1000)))
                        .ignoreContentType(true)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .timeout(12000)
                        .followRedirects(true)
                        .execute()
                        .parse();
                Element content = doc.getElementsByClass("def-panel").get(0);
                String theWord = content.getElementsByClass("def-header").get(0).child(0).html();
                String theDefinition = Jsoup.parse(content.getElementsByClass("meaning").get(0).html()).text();
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle(theWord)
                        .setDescription(theDefinition)
                        .setColor(new Color(204, 44, 44))
                        .setAuthor("Random Urban Dictionary Word", "https://www.urbandictionary.com/", "");
                channel.sendMessage(embed).exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            } catch (HttpStatusException ex) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("404 Error")
                        .setDescription("Urban Dictionary doesn't have an entry for that.")
                        .setColor(new Color(204, 44, 44))
                        .setAuthor("Random Urban Dictionary Word", "https://www.urbandictionary.com/", "");
                channel.sendMessage(embed).exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            } catch (IOException ex) {
                Logger.getLogger(UrbanDictionaryCommand.class.getName()).log(Level.SEVERE, null, ex);
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("IOException Error")
                        .setDescription("The bot couldn't connect to the server.")
                        .setColor(new Color(204, 44, 44))
                        .setAuthor("Random Urban Dictionary Word", "https://www.urbandictionary.com/", "");
                channel.sendMessage(embed).exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            }
        } else {
            StringBuilder restOfThing = new StringBuilder();
            for (String arg : args) restOfThing.append(arg).append(" ");
            try {
                Document doc = Jsoup.connect("https://www.urbandictionary.com/define.php?term=" + restOfThing.toString().trim()).get();
                Element content = doc.getElementsByClass("def-panel").get(0);
                String theWord = content.getElementsByClass("def-header").get(0).child(0).html();
                String theDefinition = Jsoup.parse(content.getElementsByClass("meaning").get(0).html()).text();
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle(theWord)
                        .setDescription(theDefinition)
                        .setColor(new Color(204, 44, 44))
                        .setAuthor("Random Urban Dictionary Word", "https://www.urbandictionary.com/", "");
                channel.sendMessage(embed).exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            } catch (HttpStatusException ex) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("404 Error")
                        .setDescription("Urban Dictionary doesn't have an entry for that.")
                        .setColor(new Color(204, 44, 44))
                        .setAuthor("Random Urban Dictionary Word", "https://www.urbandictionary.com/", "");
                channel.sendMessage(embed).exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            } catch (IOException ex) {
                Logger.getLogger(UrbanDictionaryCommand.class.getName()).log(Level.SEVERE, null, ex);
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("IOException Error")
                        .setDescription("The bot couldn't connect to the server.")
                        .setColor(new Color(204, 44, 44))
                        .setAuthor("Random Urban Dictionary Word", "https://www.urbandictionary.com/", "");
                channel.sendMessage(embed).exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            }
        }

    }
}
