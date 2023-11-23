package me.liljanosik.duels.util;

import me.liljanosik.duels.Duels;
import me.liljanosik.duels.commands.DuelsCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;

/**
 * This utility class easses the sending of messages to players while adding SFX feedback to them.
 */
public class Messager {

    /**
     * Sends the passed message to the passed CommandSender, formatting all color codes in the process.
     *
     * @param messageReceiver CommandSender that will receive the message.
     * @param message         Message that will be sent to the passed command sender.
     */
    public static void sendMessage(CommandSender messageReceiver, String message) {
        messageReceiver.sendMessage(StringUtil.formatColor(message));
    }

    /**
     * Sends the passed message to the passed CommandSender, formatting all color codes and playing a note block sound
     * in the process.
     *
     * @param messageReceiver CommandSender that will receive the message.
     * @param message         Message that will be sent to the passed command sender.
     */
    public static void sendSuccessMessage(CommandSender messageReceiver, String message) {
        sendMessage(messageReceiver, message);
        SFXManager.playSuccessSound(messageReceiver);
    }

    /**
     * Sends the passed message to the passed CommandSender, formatting all color codes and playing a note block sound
     * in the process.
     *
     * @param messageReceiver CommandSender that will receive the message.
     * @param message         Message that will be sent to the passed command sender.
     */
    public static void sendErrorMessage(CommandSender messageReceiver, String message) {
        sendMessage(messageReceiver, message);
        SFXManager.playErrorSound(messageReceiver);
    }

    /**
     * Sends a message with the help text, which contains all the subcommands the player has access to.
     *
     * @param messageReceiver CommandSender that will receiver the message.
     */
    public static void sendHelpMessage(CommandSender messageReceiver) {
        Duels plugin = Duels.getInstance();
        StringBuilder finalMessage = new StringBuilder("&c&lCommands\n");
        Iterator<DuelsCommand> subcommandIterator = plugin.getSubcommands().values().iterator();
        while (subcommandIterator.hasNext()) {
            DuelsCommand subcommand = subcommandIterator.next();
            if (!messageReceiver.hasPermission(subcommand.getPermission())) continue;
            finalMessage.append("&a").append(subcommand.getUsageMessage()).append(" &c- &e").append(subcommand.getInfoMessage());
            if (subcommandIterator.hasNext()) {
                finalMessage.append("\n");
            }
        }
        Messager.sendSuccessMessage(messageReceiver, finalMessage.toString());
    }

    /**
     * Sends an error message to the passed CommandSender indicating that it does not have the required permissions for
     * some action.
     *
     * @param messageReceiver CommandSender that will receive the message.
     */
    public static void sendNoPermissionMessage(CommandSender messageReceiver) {
        sendErrorMessage(messageReceiver, "&cYou do not have permissions to use this command!");
    }

    /**
     * Sends the passed title to the passed Player with the specified fade in, duration and fade out.
     *
     * @param messageReceiver Player that will receive the title.
     * @param title           Title that will be sent to the player.
     * @param subtitle        Subtitle that will be sent to the player.
     * @param fadeIn          Fade in time of the title in seconds.
     * @param duration        Duration of the title in seconds.
     * @param fadeOut         Fade out time of the title in seconds.
     */
    public static void sendTitleMessage(Player messageReceiver, String title, String subtitle, double fadeIn,
                                        double duration, double fadeOut) {
        if (title == null) return;

        title = StringUtil.formatColor(title);
        if (subtitle != null) {
            subtitle = StringUtil.formatColor(subtitle);
        }
        messageReceiver.sendTitle(title, subtitle, (int) fadeIn * 20, (int) duration * 20, (int) fadeOut * 20);
    }
}
