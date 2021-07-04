package de.staticred.dbv2.player.mcimplementations;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.DBUPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

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
    public long sendMessage(String message) {
        sender.sendMessage(DBUtil.getINSTANCE().getMcMessagesFileHandler().getPrefix() + message);
        return -1;
    }

    @Override
    public void kick(String reason) {
    }

    @Override
    public boolean isConsole() {
        return true;
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

    @Override
    public void sendComponent(Component component) {
        sender.sendMessage(component.toString());
    }
}
