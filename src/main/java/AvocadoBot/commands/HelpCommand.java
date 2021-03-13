package AvocadoBot.commands;

import AvocadoBot.CustomEmbedBuilder;
import AvocadoBot.Main;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import de.btobastian.sdcf4j.CommandHandler;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class HelpCommand implements CommandExecutor {
    private final CommandHandler commandHandler;

    public HelpCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Command(aliases = {"help", "commands"}, description = "Shows this page.", usage = "help")
    public void onHelpCommand(TextChannel tc) {
        EmbedBuilder eb = new CustomEmbedBuilder();
        eb.setTitle("Help Menu");
        eb.setDescription("This is a work in progress, mention the developer for help.");
        commandHandler.getCommands().stream()
                .map(CommandHandler.SimpleCommand::getCommandAnnotation)
                .filter(Command::showInHelpPage)
                .forEach(y -> eb.addInlineField("`" + Main.prefix +
                        (y.usage().isEmpty() ? y.aliases()[0] : y.usage()) + "`", y.description()));
        tc.sendMessage(eb);
    }
}
