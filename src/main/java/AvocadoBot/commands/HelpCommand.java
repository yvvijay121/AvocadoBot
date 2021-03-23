package AvocadoBot.commands;

import AvocadoBot.CustomEmbedBuilder;
import AvocadoBot.Main;
import com.vdurmont.emoji.EmojiParser;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import de.btobastian.sdcf4j.CommandHandler;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HelpCommand implements CommandExecutor {
    private final CommandHandler commandHandler;

    public HelpCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Command(aliases = {"help", "commands"}, description = "Shows this page.", usage = "help")
    public void onHelpCommand(TextChannel tc) {
        String[][] s1 = commandHandler.getCommands().stream()
                .map(CommandHandler.SimpleCommand::getCommandAnnotation)
                .filter(Command::showInHelpPage)
                .map(y -> new String[]{"`" + Main.prefix +
                        (y.usage().isEmpty() ? y.aliases()[0] : y.usage()) + "`", y.description()})
                .toArray(String[][]::new);
        List<EmbedBuilder> cebList = new ArrayList<>();
        for (int i = 0; i < s1.length; i += 4) {
            EmbedBuilder eb1 = new CustomEmbedBuilder();
            eb1.setTitle("Help Menu");
            eb1.setDescription("Page #" + ((i / 4) + 1));
            String[][] a1 = Arrays.copyOfRange(s1, i, Math.min(s1.length, i + 4));
            for (String[] a2 : a1) {
                eb1.addField(a2[0], a2[1]);
            }
            cebList.add(eb1);
        }
        try {
            Message m1 = tc.sendMessage(cebList.get(0)).get();
            m1.addReaction(EmojiParser.parseToUnicode(":rewind:"));
            m1.addReaction(EmojiParser.parseToUnicode(":arrow_left:"));
            m1.addReaction(EmojiParser.parseToUnicode(":arrow_right:"));
            m1.addReaction(EmojiParser.parseToUnicode(":fast_forward:"));
            AtomicInteger i = new AtomicInteger();
            m1.addReactionAddListener(reactionEvent -> reactionEvent.getUser().ifPresent(user -> {
                if (!user.isBot()) {
                    if (reactionEvent.getEmoji().equalsEmoji(EmojiParser.parseToUnicode(":rewind:"))) {
                        m1.edit(cebList.get(0));
                        i.set(0);
                        m1.removeReactionsByEmoji(user, EmojiParser.parseToUnicode(":rewind:"));
                    } else if (reactionEvent.getEmoji().equalsEmoji(EmojiParser.parseToUnicode(":arrow_right:"))) {
                        i.getAndIncrement();
                        if (i.get() <= cebList.size() - 1) m1.edit(cebList.get(i.get()));
                        m1.removeReactionsByEmoji(user, EmojiParser.parseToUnicode(":arrow_right:"));
                    } else if (reactionEvent.getEmoji().equalsEmoji(EmojiParser.parseToUnicode(":arrow_left:"))) {
                        if (i.get() != 0) i.getAndDecrement();
                        m1.edit(cebList.get(i.get()));
                        m1.removeReactionsByEmoji(user, EmojiParser.parseToUnicode(":arrow_left:"));
                    } else if (reactionEvent.getEmoji().equalsEmoji(EmojiParser.parseToUnicode(":fast_forward:"))) {
                        m1.edit(cebList.get(cebList.size() - 1));
                        m1.removeReactionsByEmoji(user, EmojiParser.parseToUnicode(":fast_forward:"));
                    }
                }
            }));
        } catch (Exception e) {
            tc.sendMessage("something went wrong");
        }
    }
}
