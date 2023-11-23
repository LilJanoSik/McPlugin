package me.liljanosik.duels.util;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class handles different ways of playing SFX to either players or specific areas.
 */
public class SFXManager {

    /**
     * Plays a note block pling sound to the passed CommandSender.
     *
     * @param commandSender CommandSender the sound will be played to.
     */
    public static void playSuccessSound(CommandSender commandSender) {
        playPlayerSound(commandSender, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
    }

    /**
     * Plays a note block bass sound to the passed CommandSender.
     *
     * @param commandSender CommandSender the sound will be played to.
     */
    public static void playErrorSound(CommandSender commandSender) {
        playPlayerSound(commandSender, Sound.BLOCK_NOTE_BLOCK_BASS, 1, 2);
    }

    /**
     * Plays the passed sound with the specified volume and pitch to the passed CommandSender. If the CommandSender
     * isn't a player then don't play the sound.
     *
     * @param sender CommandSender the sound will be played to.
     * @param sound  Sound that will be played.
     * @param volume Volume the sound will have while playing.
     * @param pitch  Pitch the sound will have while playing.
     */
    public static void playPlayerSound(CommandSender sender, Sound sound, float volume, float pitch) {
        if (!(sender instanceof Player)) return;

        Player player = (Player) sender;
        player.playSound(player.getLocation(), sound, volume, pitch);
    }
}
