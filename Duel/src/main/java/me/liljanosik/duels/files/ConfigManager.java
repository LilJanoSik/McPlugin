package me.liljanosik.duels.files;

import me.liljanosik.duels.Duels;
import me.liljanosik.duels.util.LogUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private static final Duels PLUGIN = Duels.getInstance();

    /**
     * Returns the configured arena location under the specified number.
     *
     * @param number Number that will be searched.
     * @return Configured arena location under the specified number.
     */
    public static Location getArenaLocation(int number) {
        ConfigurationSection locationSection = getConfig().getConfigurationSection("arena-locations." + number);
        if (locationSection == null) return null;
        int x = locationSection.getInt("x");
        int y = locationSection.getInt("y");
        int z = locationSection.getInt("z");
        String worldName = locationSection.getString("world");
        if (worldName == null) {
            LogUtil.sendWarnLog("World name not provided for arena location '" + number + "'.");
            return null;
        }
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            LogUtil.sendWarnLog("Unknown world '" + worldName + "' for arena location '" + number + "'.");
            return null;
        }
        return new Location(world, x, y, z);
    }

    /**
     * Returns a FileConfigurations instance of the config.yml file.
     *
     * @return FileConfigurations instance of the config.yml file.
     */
    private static FileConfiguration getConfig() {
        return PLUGIN.getConfig();
    }
}
