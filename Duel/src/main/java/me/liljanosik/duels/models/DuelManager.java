package me.liljanosik.duels.models;

import me.liljanosik.duels.files.ConfigManager;
import me.liljanosik.duels.util.Messager;
import me.liljanosik.duels.util.SFXManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DuelManager {
    private final Set<UUID> duelPlayers = new HashSet<>();
    private boolean hasRunningDuel;

    /**
     * Starts the duel for the to passed players.
     *
     * @param challenger Player that started the duel.
     * @param challenged Player that accepted the duel.
     */
    public void startDuel(Player challenger, Player challenged) {
        Location arenaLocation1 = ConfigManager.getArenaLocation(1);
        Location arenaLocation2 = ConfigManager.getArenaLocation(2);
        if (arenaLocation1 == null || arenaLocation2 == null) return;

        this.hasRunningDuel = true;
        duelPlayers.add(challenger.getUniqueId());
        duelPlayers.add(challenged.getUniqueId());
        // Teleport players
        challenger.teleport(arenaLocation1);
        SFXManager.playPlayerSound(challenger, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 0.6F, 1.3F);
        challenged.teleport(arenaLocation2);
        SFXManager.playPlayerSound(challenged, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 0.6F, 1.3F);
        // Equip items
        equipPlayerItems(challenger);
        equipPlayerItems(challenged);
    }

    /**
     * Equips all the duel items to the passed player.
     *
     * @param player Player that will be equipped with the duel items.
     */
    private void equipPlayerItems(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        playerInventory.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        playerInventory.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        playerInventory.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        giveItem(player, new ItemStack(Material.DIAMOND_SWORD));
        // Creating and equipping heal potions
        ItemStack potion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
        potion.setItemMeta(meta);
        for (int i = 0; i < 3; i++) {
            giveItem(player, potion);
        }
    }

    /**
     * Gives the passed item to the passed player, if the player has no inventory space it drops it next to them.
     *
     * @param player Player that will receive the item.
     * @param item   Item that will be given.
     */
    private void giveItem(Player player, ItemStack item) {
        PlayerInventory inventory = player.getInventory();
        if (inventory.firstEmpty() != -1) {
            inventory.addItem(item);
        } else {
            player.getWorld().dropItem(player.getLocation(), item);
        }
    }

    /**
     * Announces the passed winner, clears the player's inventories and teleports them to their bed spawns.
     *
     * @param winner Player that won the duel.
     */
    public void endDuel(Player winner) {
        for (UUID uuid : duelPlayers) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;

            // Announce the winner
            Messager.sendTitleMessage(player, "&bThe Duel is Over!", "&eThe winner is &l" + winner.getDisplayName(),
                    1, 3, 1);
            SFXManager.playPlayerSound(player, Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.6F, 1.2F);
            // Teleport player to their spawn
            if (player.getBedSpawnLocation() != null) {
                player.teleport(player.getBedSpawnLocation());
            } else {
                player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
            }
            // Clear their inventory and respawn them if needed
            player.getInventory().clear();
            player.spigot().respawn();
            SFXManager.playPlayerSound(player, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 0.6F, 1.2F);
        }
        this.hasRunningDuel = false;
        duelPlayers.clear();
    }

    public Set<UUID> getDuelPlayers() {
        return duelPlayers;
    }

    public boolean hasRunningDuel() {
        return hasRunningDuel;
    }
}
