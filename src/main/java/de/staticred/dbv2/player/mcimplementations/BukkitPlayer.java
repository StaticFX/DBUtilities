package de.staticred.dbv2.player.mcimplementations;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.proxies.bukkit.DBVerifierPlugin;
import net.kyori.adventure.text.Component;
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
    public long sendMessage(String message) {
        bukkitPlayer.sendMessage(DBUtil.getINSTANCE().getMcMessagesFileHandler().getPrefix() + message);
        return -1;
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
    public boolean isConsole() {
        return false;
    }

    @Override
    public void sendMessageRaw(String message) {
        sendMessage(message);
    }

    @Override
    public UUID getUUID() {
        return bukkitPlayer.getUniqueId();
    }

    @Override
    public String getName() {
        return bukkitPlayer.getName();
    }

    @Override
    public void sendComponent(Component component) {
        DBVerifierPlugin.getInstance().getBukkitAudiences().player(bukkitPlayer).sendMessage(component);
    }
}
