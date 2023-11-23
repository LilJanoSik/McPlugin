package me.liljanosik.duels;

import me.liljanosik.duels.commands.*;
import me.liljanosik.duels.listeners.DuelHandler;
import me.liljanosik.duels.models.DuelManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Duels extends JavaPlugin {
    /**
     * HashMap that links all the subcommand Strings with their respective subcommand instance.
     */
    //           CommandName, SubcommandInstance
    private final Map<String, DuelsCommand> subcommands = new LinkedHashMap<>();
    /**
     * HashMap that links the player that has been challenged to a duel with the player that challenged them.
     */
    //           Challenged, Challener
    private final Map<UUID, UUID> duelPetitions = new HashMap<>();
    private DuelManager duelManager;

    /**
     * Creates and returns an static instance of the Plugin's main class.
     *
     * @return Instance of the main class of the plugin.
     */
    public static Duels getInstance() {
        return Duels.getPlugin(Duels.class);
    }

    @Override
    public void onEnable() {
        configureFiles();
        registerManagers();
        registerCommands();
        registerListeners();
    }

    /**
     * Creates and configures all the files regarding the plugin.
     * Ex. config.yml file.
     */
    private void configureFiles() {
        saveDefaultConfig();
    }

    /**
     * Registers all the needed managers in order for the plugin to work.
     */
    private void registerManagers() {
        this.duelManager = new DuelManager();
    }

    /**
     * Registers all the commands, subcommands, CommandExecutors and TabCompleters regarding the plugin.
     */
    private void registerCommands() {
        subcommands.put("pvp", new CommandPvp(this));
        subcommands.put("accept", new CommandAccept(this));
        subcommands.put("help", new CommandHelp());
        subcommands.put("reload", new CommandReload(this));
        getCommand("duels").setExecutor(new CommandRunner(this));
        getCommand("duels").setTabCompleter(new CommandCompleter(this));
    }

    /**
     * Registers all the event listeners the plugin might need.
     */
    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new DuelHandler(this), this);
    }

    @Override
    public void onDisable() {

    }

    /**
     * Returns an instance of the subcommands HashMap.
     *
     * @return Instance of the subcommands HashMap.
     */
    public Map<String, DuelsCommand> getSubcommands() {
        return subcommands;
    }

    public Map<UUID, UUID> getDuelPetitions() {
        return duelPetitions;
    }

    public DuelManager getDuelManager() {
        return duelManager;
    }
}
