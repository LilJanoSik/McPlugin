package me.liljanosik.duels.commands;

import me.liljanosik.duels.Duels;
import me.liljanosik.duels.util.Messager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class controls everything regarding command execution in the plugin. It checks the command and arguments
 * every time the player runs a command registered by the plugin.
 */
public class CommandRunner implements CommandExecutor {
    private final Duels plugin;

    public CommandRunner(Duels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("duels")) return false;
        // If the player only issued the name of the plugin/command send the help text
        if (args.length < 1) {
            Messager.sendHelpMessage(sender);
            return true;
        }
        String inputCommand = args[0].toLowerCase();
        if (!plugin.getSubcommands().containsKey(inputCommand)) {
            // Player inputted an unknown subcommand
            Messager.sendErrorMessage(sender, "&cUnknown command. Type &l/duels help &cto see the full " +
                    "command list.");
            return true;
        }
        DuelsCommand subcommand = plugin.getSubcommands().get(inputCommand);
        if (subcommand.isPlayerCommand() && !(sender instanceof Player)) {
            Messager.sendErrorMessage(sender, "&cNot available for consoles.");
            return true;
        }
        if (subcommand.isConsoleCommand() && sender instanceof Player) {
            Messager.sendErrorMessage(sender, "&cNot available for players.");
            return true;
        }
        if (args.length < subcommand.getArgumentLength()) {
            Messager.sendErrorMessage(sender, "&cUsage: &l" + subcommand.getUsageMessage());
            return true;
        }
        if (!sender.hasPermission(subcommand.getPermission())) {
            Messager.sendNoPermissionMessage(sender);
            return true;
        }
        subcommand.execute(sender, args);
        return true;
    }
}
