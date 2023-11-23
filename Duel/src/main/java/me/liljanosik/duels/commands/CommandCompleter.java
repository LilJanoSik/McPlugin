package me.liljanosik.duels.commands;

import me.liljanosik.duels.Duels;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class handles the tab completion system, adding commands to the tab completer only if the player has
 * the required permissions.
 */
public class CommandCompleter implements TabCompleter {
    private final Duels plugin;

    public CommandCompleter(Duels plugin) {
        this.plugin = plugin;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> arguments = new ArrayList<>();
        Collection<DuelsCommand> subcommands = plugin.getSubcommands().values();
        // Add all the subcommands the player has access to to the tab completion
        for (DuelsCommand subcommand : subcommands) {
            if (!sender.hasPermission(subcommand.getPermission())) continue;

            arguments.add(subcommand.getName());
        }
        DuelsCommand subcommand = plugin.getSubcommands().get(args[0]);
        if (args.length > 1 && (subcommand == null || !sender.hasPermission(subcommand.getPermission()))) {
            // If the player already wrote a command it doesn't have access to or an invalid command then return an
            // empty tab completion
            arguments.clear();
            return arguments;
        }
        if (args.length < 2) {
            return getCompletion(arguments, args, 0);
        } else if (args.length < 3) {
            if (subcommand.getName().equals("pvp")) return null;
        }
        arguments.clear();
        return arguments;
    }

    /**
     * Searches for matches between the passed arguments and the passed String[] based on the specified index.
     *
     * @param arguments Possible arguments the player can type into the tab completion.
     * @param args      Arguments the player is currently using in the command completion.
     * @param index     Index the completion will be added to.
     * @return ArrayList with all the matching completions.
     */
    private ArrayList<String> getCompletion(ArrayList<String> arguments, String[] args, int index) {
        ArrayList<String> results = new ArrayList<>();
        for (String argument : arguments) {
            if (!argument.toLowerCase().startsWith(args[index].toLowerCase())) continue;

            results.add(argument);
        }
        return results;
    }
}
