package AvocadoBot.commands.testing;

import com.vdurmont.emoji.EmojiParser;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.concurrent.ExecutionException;

public class ReactionTestCommand2 implements MessageCreateListener {
    private static String prefix;

    public ReactionTestCommand2(String pre) {
        prefix = pre;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equalsIgnoreCase(prefix + "arrow")) {
            TextChannel channel = event.getChannel();
            Message m1;
            try {
                m1 = channel.sendMessage("hello").get();
            } catch (InterruptedException | ExecutionException e) {
                channel.sendMessage("Something went wrong.");
                return;
            }
            m1.addReaction(EmojiParser.parseToUnicode(":arrow_left:"));
            m1.addReaction(EmojiParser.parseToUnicode(":arrow_right:"));
            m1.addReaction(EmojiParser.parseToUnicode(":arrow_up:"));
            m1.addReaction(EmojiParser.parseToUnicode(":arrow_down:"));
            m1.addReactionAddListener(reactionEvent -> reactionEvent.getUser().ifPresent(user -> {
                if (!user.isBot()) {
                    if (reactionEvent.getEmoji().equalsEmoji(EmojiParser.parseToUnicode(":arrow_left:"))) {
                        m1.edit("hello left");
                        m1.removeReactionsByEmoji(user, EmojiParser.parseToUnicode(":arrow_left:"));
                    } else if (reactionEvent.getEmoji().equalsEmoji(EmojiParser.parseToUnicode(":arrow_right:"))) {
                        m1.edit("hello right");
                        m1.removeReactionsByEmoji(user, EmojiParser.parseToUnicode(":arrow_right:"));
                    } else if (reactionEvent.getEmoji().equalsEmoji(EmojiParser.parseToUnicode(":arrow_down:"))) {
                        m1.edit("hello down");
                        m1.removeReactionsByEmoji(user, EmojiParser.parseToUnicode(":arrow_down:"));
                    } else if (reactionEvent.getEmoji().equalsEmoji(EmojiParser.parseToUnicode(":arrow_up:"))) {
                        m1.edit("hello up");
                        m1.removeReactionsByEmoji(user, EmojiParser.parseToUnicode(":arrow_up:"));
                    }
                }
            }));
        }
    }
}