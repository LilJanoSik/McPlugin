package me.liljanosik.duels.commands;

import me.liljanosik.duels.util.Messager;
import org.bukkit.command.CommandSender;

/**
 * / help command. Displays the help text for the plugin.
 */
public class CommandHelp extends DuelsCommand {

    public CommandHelp() {
        setName("help");
        setInfoMessage("Displays this list.");
        setPermission("duels.help");
        setArgumentLength(1);
        setUsageMessage("/duels help");
        setUniversalCommand(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Messager.sendHelpMessage(sender);
    }
}
