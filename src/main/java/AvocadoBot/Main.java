/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AvocadoBot;

import AvocadoBot.commands.HelpCommand;
import AvocadoBot.commands.PingCommand;
import AvocadoBot.commands.fun.UrbanDictionaryCommand;
import AvocadoBot.commands.fun.XKCDCommand;
import AvocadoBot.commands.moderation.BanCommand;
import AvocadoBot.commands.moderation.ClearCommand;
import AvocadoBot.commands.moderation.KickCommand;
import AvocadoBot.commands.moderation.MuteCommand;
import AvocadoBot.commands.moderation.TempMuteCommand;
import AvocadoBot.commands.moderation.UnbanCommand;
import AvocadoBot.commands.moderation.UnmuteCommand;
import AvocadoBot.commands.moderation.UserInfoCommand;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

public class Main {

    private static final String prefix = "a!";
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        // Enable debugging, if no slf4j logger was found
        FallbackLoggerConfiguration.setDebug(true);
        
        if (args.length != 1) {
            System.err.println("This bot requires the bot's token as a command-line argument");
            System.exit(1);
        }
        
        String token = args[0];
        
        // We login blocking, just because it is simpler and doesn't matter here
        DiscordApi api = new DiscordApiBuilder().setToken(token).setAllIntents().login().join();

        // Print the invite url of the bot
        logger.info("You can invite me by using the following url: " + api.createBotInvite());
        System.out.println(api.createBotInvite());

        // Add listeners
        api.addMessageCreateListener(new PingCommand(prefix));
        api.addMessageCreateListener(new HelpCommand(prefix));
        api.addMessageCreateListener(new KickCommand(prefix));
        api.addMessageCreateListener(new BanCommand(prefix));
        api.addMessageCreateListener(new UnbanCommand(prefix));
        api.addMessageCreateListener(new MuteCommand(prefix));
        api.addMessageCreateListener(new UnmuteCommand(prefix));
        api.addMessageCreateListener(new TempMuteCommand(prefix));
        api.addMessageCreateListener(new ClearCommand(prefix));
        api.addMessageCreateListener(new UserInfoCommand(prefix));
        api.addMessageCreateListener(new UrbanDictionaryCommand(prefix));
        api.addMessageCreateListener(new XKCDCommand(prefix));
        // Log a message, if the bot joined or left a server
        api.addServerJoinListener(event -> logger.info("Joined server " + event.getServer().getName()));
        api.addServerLeaveListener(event -> logger.info("Left server " + event.getServer().getName()));

    }

}
