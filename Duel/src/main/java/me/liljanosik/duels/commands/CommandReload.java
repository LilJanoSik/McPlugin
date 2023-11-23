package me.liljanosik.duels.commands;

import me.liljanosik.duels.Duels;
import me.liljanosik.duels.util.Messager;
import org.bukkit.command.CommandSender;

/**
 * / reload command. Reloads the plugin's configurations.
 */
public class CommandReload extends DuelsCommand {
    private final Duels plugin;

    public CommandReload(Duels plugin) {
        this.plugin = plugin;

        setName("reload");
        setInfoMessage("Reloads the plugin's config.");
        setPermission("duels.reload");
        setArgumentLength(1);
        setUsageMessage("/duels reload");
        setUniversalCommand(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        plugin.reloadConfig();
        Messager.sendSuccessMessage(sender, "&aConfig reloaded.");
    }
}
