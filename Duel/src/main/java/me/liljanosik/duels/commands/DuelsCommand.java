package me.liljanosik.duels.commands;

import org.bukkit.command.CommandSender;

/**
 * This abstract class eases the management of subcommands by allowing them to override the execute method as needed.
 * It also helps to manage permissions, arguments and target users.
 */
public abstract class DuelsCommand {
    private String name;
    private String infoMessage;
    private String permission;
    private String usageMessage;
    private int argumentLength;
    private boolean isConsoleCommand;
    private boolean isPlayerCommand;
    private boolean isUniversalCommand;

    public abstract void execute(CommandSender sender, String[] args);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getArgumentLength() {
        return argumentLength;
    }

    public void setArgumentLength(int argumentLength) {
        this.argumentLength = argumentLength;
    }

    public String getUsageMessage() {
        return usageMessage;
    }

    public void setUsageMessage(String usageMessage) {
        this.usageMessage = usageMessage;
    }

    public boolean isConsoleCommand() {
        return isConsoleCommand;
    }

    public void setConsoleCommand(boolean isConsoleCommand) {
        this.isConsoleCommand = isConsoleCommand;
    }

    public boolean isPlayerCommand() {
        return isPlayerCommand;
    }

    public void setPlayerCommand(boolean isPlayerCommand) {
        this.isPlayerCommand = isPlayerCommand;
    }

    public boolean isUniversalCommand() {
        return isUniversalCommand;
    }

    public void setUniversalCommand(boolean isUniversalCommand) {
        this.isUniversalCommand = isUniversalCommand;
    }
}
