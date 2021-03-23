package AvocadoBot.commands.utility;

import AvocadoBot.CustomEmbedBuilder;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.List;

public class RemoveRoleCommand implements CommandExecutor {
    @Command(aliases = {"removerole", "rrole"},
            description = "Removes the role (given the role id) from the mentioned user," +
                    "or the user that called the command.",
            usage = "removerole [<user mentioned>] <role id>")
    public void onMessageCreate(String[] args, User u, Message m, Server s, TextChannel tc) {
        //NOTE: ADD RESTRICTIONS
        List<User> userList = m.getMentionedUsers();
        User b = u;
        if (userList.size() > 0) {
            b = userList.get(0);
        }
        User finalB = b;
        s.getRoleById(args[1]).ifPresent(role -> finalB.removeRole(role).thenAccept(a -> tc.sendMessage(new CustomEmbedBuilder()
                .setTitle("Removed Role.")
                .setDescription("Removed role " + role.getMentionTag() + " to " + finalB.getDiscriminatedName() + "."))).exceptionally(c -> {
            tc.sendMessage(new CustomEmbedBuilder()
                    .setTitle("Error.")
                    .setDescription("An exception occurred while trying to give the role "
                            + role.getMentionTag() + " to " + finalB.getDiscriminatedName() + "."));
            return null;
        }));
    }
}
