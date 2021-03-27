package AvocadoBot.commands.fun;

import AvocadoBot.CustomEmbedBuilder;
import com.vdurmont.emoji.EmojiParser;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.util.Locale;
import java.util.Random;

public class EightBallCommand implements CommandExecutor {
    @Command(aliases = {"8ball", "eightball"}, description = "Ask a question from the eight ball!", usage = "8ball <question>")
    public void onCommandExecution(User u, TextChannel tc, String[] args) {
        String[] asdf = {"It is certain.",
                "It is decidedly so.",
                "Without a doubt.",
                "Yes – definitely.",
                "You may rely on it.",
                "As I see it, yes.",
                "Most likely.",
                "Outlook good.",
                "Yes.",
                "Signs point to yes.",
                "Reply hazy, try again.",
                "Ask again later.",
                "Better not tell you now.",
                "Cannot predict now.",
                "Concentrate and ask again.",
                "Don't count on it.",
                "My reply is no.",
                "My sources say no.",
                "Outlook not so good.",
                "Very doubtful."};
        EmbedBuilder ce1 = new CustomEmbedBuilder();
        ce1.setTitle(EmojiParser.parseToUnicode(":8ball:") + " Eight Ball Prediction\n" + u.getDiscriminatedName() + " asks: " + String.join(" ", args));
        int rnd = new Random().nextInt(asdf.length);
        ce1.setDescription("**```" + asdf[rnd].toUpperCase(Locale.ROOT) + "```**");
        tc.sendMessage(ce1);

    }
}
