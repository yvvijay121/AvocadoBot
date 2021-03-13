package AvocadoBot.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

public class PingCommand implements CommandExecutor {
    @Command(aliases = {"ping", "?"},
            description = "Just a testing command.",
            usage = "ping")
    public String onMessageCreate() {
        return "Pong!";
    }
}