package me.liljanosik.duels.commands;

import me.liljanosik.duels.Duels;
import me.liljanosik.duels.util.Messager;
import me.liljanosik.duels.util.SFXManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandPvp extends DuelsCommand {
    private final Duels plugin;

    public CommandPvp(Duels plugin) {
        this.plugin = plugin;

        setName("pvp");
        setInfoMessage("Challenges the passed player to a PvP duel.");
        setPermission("duels.pvp");
        setUsageMessage("/duels pvp <Player>");
        setArgumentLength(2);
        setPlayerCommand(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (plugin.getDuelManager().hasRunningDuel()) {
            Messager.sendErrorMessage(player, "&cThere is already a duel running.");
            return;
        }
        String targetName = args[1];
        Player target = Bukkit.getPlayerExact(targetName);
        if (target == null) {
            Messager.sendErrorMessage(player, "&cUnknown player &l" + targetName + "&c.");
            return;
        }
        if (target.equals(player)) {
            Messager.sendErrorMessage(player, "&cYou can't challenge yourself to a duel.");
            return;
        }
        UUID playerUuid = player.getUniqueId();
        if (plugin.getDuelPetitions().containsValue(playerUuid)) {
            Messager.sendErrorMessage(player, "&cYou have already challenged another player to a duel.");
            return;
        }
        UUID targetUuid = target.getUniqueId();
        if (plugin.getDuelPetitions().containsKey(targetUuid)) {
            Messager.sendErrorMessage(player, "&c&l" + targetName + " &chas already been challenged to a duel.");
            return;
        }
        plugin.getDuelPetitions().put(targetUuid, player.getUniqueId());
        Messager.sendSuccessMessage(player, "&aYou have successfully challenged &l" + target.getDisplayName() +
                " &ato a duel.");
        Messager.sendMessage(target, "&eYou have been challenged to a duel by &l" + player.getDisplayName() + "&e. \n" +
                "Type &l/duels accept &eto accept it.");
        SFXManager.playPlayerSound(target, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 0.6F, 1.3F);
        // Set petition expiration
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            UUID challengerUuid = plugin.getDuelPetitions().remove(targetUuid);
            if (challengerUuid == null || !challengerUuid.equals(playerUuid)) return;

            Messager.sendErrorMessage(player, "&cYou duel petition to &l" + target.getDisplayName() + " &chas " +
                    "expired.");
            Messager.sendErrorMessage(target, "&c&l" + player.getDisplayName() + "'s &cduel's petition has " +
                    "expired.");
        }, 600L);
    }
}
