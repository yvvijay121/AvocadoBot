package AvocadoBot;

import AvocadoBot.commands.PingCommand;
import AvocadoBot.commands.fun.JokeCommand;
import AvocadoBot.commands.fun.MemeCommand;
import AvocadoBot.commands.fun.UrbanDictionaryCommand;
import AvocadoBot.commands.fun.XKCDCommand;
import AvocadoBot.commands.moderation.*;
import AvocadoBot.commands.HelpCommand;
import AvocadoBot.commands.testing.ReactionTestCommand2;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.JavacordHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static final String prefix = "a!";
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static DiscordApi api;

    public static void main(String[] args) {

        // Enable debugging, if no slf4j logger was found
        FallbackLoggerConfiguration.setDebug(true);
        Properties properties = new Properties();

        try (InputStream is = Main.class.getResourceAsStream("/AppTokenData.properties")) {
            properties.load(is);
        } catch (IOException e) {
            System.out.println("Can't find the .properties file. Exiting the program.");
            System.exit(1);
        }

        String token = properties.getProperty("DiscordDeveloperToken");

        // We login blocking, just because it is simpler and doesn't matter here
        DiscordApi api = new DiscordApiBuilder().setToken(token).setAllIntents().login().join();
        // Print the invite url of the bot
        logger.info("You can invite me by using the following url: " + api.createBotInvite());
        System.out.println(api.createBotInvite());

        CommandHandler handler = new JavacordHandler(api);
        handler.setDefaultPrefix(prefix);
        handler.registerCommand(new PingCommand());
        handler.registerCommand(new JokeCommand());
        handler.registerCommand(new MemeCommand());
        handler.registerCommand(new UrbanDictionaryCommand());
        handler.registerCommand(new XKCDCommand());
        handler.registerCommand(new KickCommand());
        handler.registerCommand(new ClearCommand());
        handler.registerCommand(new MuteCommand());
        handler.registerCommand(new BanCommand());
        handler.registerCommand(new ServerInfoCommand());
        handler.registerCommand(new UnbanCommand());
        handler.registerCommand(new UserInfoCommand());
        handler.registerCommand(new HelpCommand(handler));

        // Add listeners
        api.addMessageCreateListener(new TempMuteCommand(prefix));
        api.addMessageCreateListener(new ReactionTestCommand2(prefix));
        // Log a message, if the bot joined or left a server
        api.addServerJoinListener(event -> logger.info("Joined server " + event.getServer().getName()));
        api.addServerLeaveListener(event -> logger.info("Left server " + event.getServer().getName()));

    }

}
