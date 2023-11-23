package me.liljanosik.duels.listeners;

import me.liljanosik.duels.Duels;
import me.liljanosik.duels.models.DuelManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class DuelHandler implements Listener {
    private final Duels plugin;
    private final DuelManager duelManager;

    public DuelHandler(Duels plugin) {
        this.plugin = plugin;
        this.duelManager = plugin.getDuelManager();
    }

    /**
     * Listens when a player dies. If it is a duel player then finish that duel.
     *
     * @param event PlayerDeathEvent.
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (!duelManager.getDuelPlayers().contains(player.getUniqueId())) return;

        Player killer = player.getKiller();
        if (killer == null) {
            killer = getRemainingDuelPlayer(player);
        }
        Player finalKiller = killer;
        Bukkit.getScheduler().runTaskLater(plugin, () -> duelManager.endDuel(finalKiller), 0L);
    }

    /**
     * Listens when a player quits the server. If the server was in a duel finish the duel with the remaining player
     * as the winner.
     *
     * @param event PlayerQuitEvent.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!duelManager.getDuelPlayers().contains(player.getUniqueId())) return;

        Player duelWinner = getRemainingDuelPlayer(player);
        duelManager.endDuel(duelWinner);
    }

    /**
     * Gets the remaining player of the duel the passed player is currently in.
     *
     * @param duelPlayer Player that is in a duel.
     * @return Remaining player of the duel the passed player is currently in.
     */
    private Player getRemainingDuelPlayer(Player duelPlayer) {
        UUID remainingUuid = null;
        for (UUID uuid : duelManager.getDuelPlayers()) {
            if (duelPlayer.getUniqueId().equals(uuid)) continue;

            remainingUuid = uuid;
        }
        if (remainingUuid == null) return null;

        return Bukkit.getPlayer(remainingUuid);
    }
}
