package de.staticred.dbv2.player;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * BukkitPlayer
 *
 * @author Devin
 * @version 1.0.0
 */
public class BukkitPlayer implements DBUPlayer {

    private final Player bukkitPlayer;

    /**
     * Constructor to constructe an Player object based on a ProxiedPlayer
     * @see ProxiedPlayer
     * @param bukkitPlayer based on
     */
    public BukkitPlayer(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
    }


    @Override
    public void sendMessage(String message) {
        bukkitPlayer.sendMessage(message);
    }

    @Override
    public void kick(String reason) {
        bukkitPlayer.kickPlayer(reason);
    }

    @Override
    public boolean hasPermission(String permission) {
        return bukkitPlayer.hasPermission(permission);
    }


    @Override
    public UUID getUUID() {
        return bukkitPlayer.getUniqueId();
    }

    @Override
    public String getName() {
        return bukkitPlayer.getName();
    }
}
