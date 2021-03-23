package AvocadoBot.commands.utility;

import AvocadoBot.CustomEmbedBuilder;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.stream.Collectors;


public class RoleInfoCommand implements CommandExecutor {

    private String sf(String s) {
        return "```" + s + "```";
    }

    @Command(aliases = {"roleinfo", "rinfo"},
            description = "Gets information about the role, provided either the mentioned role or the role ID.",
            usage = "rinfo (<role id> | <mentioned role>)")
    public void onMessageCreate(String[] args, User u, Message m, Server s, TextChannel tc) {
        Role r;
        if (m.getMentionedRoles().size() > 0) {
            r = m.getMentionedRoles().get(0);
        } else {
            r = s.getRoleById(args[0]).orElse(s.getEveryoneRole());
        }
        EmbedBuilder e1 = new CustomEmbedBuilder();
        e1.setTitle("Role Information: " + r.getName());
        r.getColor().ifPresent(color -> {
            String hex = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
            e1.addInlineField("Color", sf(hex));
        });
        e1.addInlineField("Mentionable?", sf(r.isMentionable() ? "true" : "false"));
        e1.addInlineField("Displayed Separately?", sf(r.isDisplayedSeparately() ? "true" : "false"));
        e1.addInlineField("Managed?", sf(r.isManaged() ? "true" : "false"));
        e1.addInlineField("# of Users With Role", sf(Integer.toString(r.getUsers().size())));
        e1.addInlineField("ID #", sf(r.getIdAsString()));
        e1.addField("Permissions", sf(r.getPermissions().getAllowedPermission().stream().map(Enum::toString).collect(Collectors.joining(", "))));
        tc.sendMessage(e1);
    }
}
