package de.staticred.dbv2.player;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

/**
 * Bungee console
 *
 * @author Devin
 * @version 1.0.0
 */
public class BungeeConsole implements DBUPlayer {

    private final CommandSender sender;


    public BungeeConsole(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(new TextComponent(message));
    }

    @Override
    public void kick(String reason) {
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
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
