package me.liljanosik.duels.commands;

import me.liljanosik.duels.Duels;
import me.liljanosik.duels.util.Messager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandAccept extends DuelsCommand {
    private final Duels plugin;

    public CommandAccept(Duels plugin) {
        this.plugin = plugin;

        setName("accept");
        setInfoMessage("Accepts your current duel invitation.");
        setPermission("duels.accept");
        setUsageMessage("/duels accept");
        setArgumentLength(1);
        setPlayerCommand(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID playerUuid = player.getUniqueId();
        if (!plugin.getDuelPetitions().containsKey(playerUuid)) {
            Messager.sendErrorMessage(player, "&cYou don't have any duel petitions right now.");
            return;
        }
        if (plugin.getDuelManager().hasRunningDuel()) {
            Messager.sendErrorMessage(player, "&cThere is already a duel running.");
            return;
        }
        UUID challengerUuid = plugin.getDuelPetitions().remove(playerUuid);
        Player challenger = Bukkit.getPlayer(challengerUuid);
        if (challenger == null) {
            Messager.sendErrorMessage(player, "&cYour challenger is no longer online.");
            return;
        }
        plugin.getDuelManager().startDuel(challenger, player);
    }
}
