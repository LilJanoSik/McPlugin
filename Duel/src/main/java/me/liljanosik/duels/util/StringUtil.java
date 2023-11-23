package me.liljanosik.duels.util;

import org.bukkit.ChatColor;

/**
 * This utility class helps to format or parse Strings to other Strings or numeric values.
 */
public class StringUtil {

    /**
     * Translates all the color codes on the passed message and returns it back.
     *
     * @param text Text whose color codes will be translated.
     * @return String with all the translated color codes.
     */
    public static String formatColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
