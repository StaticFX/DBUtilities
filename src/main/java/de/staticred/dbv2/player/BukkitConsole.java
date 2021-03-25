package de.staticred.dbv2.player;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.UUID;

/**
 * Console on the bukkit side
 *
 * @author Devin
 * @version 1.0.0
 */
public class BukkitConsole implements DBUPlayer {

    private final CommandSender sender;

    public BukkitConsole(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    @Override
    public void kick(String reason) {
    }

    @Override
    public void sendMessageRaw(String message) {
        sendMessage(message);
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public UUID getUUID() {
        return null;
    }

    @Override
    public String getName() {
        return "console";
    }
}
