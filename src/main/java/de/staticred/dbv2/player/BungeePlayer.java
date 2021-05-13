package de.staticred.dbv2.player;

import de.staticred.dbv2.DBUtil;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

/**
 * BungeeCord Player
 *
 * @author Devin
 * @version 1.0.0
 */
public class BungeePlayer implements DBUPlayer {

    private final ProxiedPlayer bungeeCordPlayer;

    /**
     * Constructor to construct a Player object based on a ProxiedPlayer
     * @see ProxiedPlayer
     * @param bungeeCordPlayer based on
     */
    public BungeePlayer(ProxiedPlayer bungeeCordPlayer) {
        this.bungeeCordPlayer = bungeeCordPlayer;
    }

    @Override
    public void sendMessage(String message) {
        bungeeCordPlayer.sendMessage(new TextComponent(DBUtil.getINSTANCE().getMcMessagesFileHandler().getPrefix() + message));
    }

    @Override
    public void sendMessageRaw(String message) {
        sendMessage(message);
    }

    @Override
    public void kick(String reason) {
        bungeeCordPlayer.disconnect(new TextComponent(reason));
    }

    @Override
    public boolean isConsole() {
        return false;
    }

    @Override
    public boolean hasPermission(String permission) {
        return bungeeCordPlayer.hasPermission(permission);
    }

    @Override
    public UUID getUUID() {
        return bungeeCordPlayer.getUniqueId();
    }

    @Override
    public String getName() {
        return bungeeCordPlayer.getName();
    }
}
