package de.staticred.dbv2.player.mcimplementations;


import com.velocitypowered.api.proxy.Player;
import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.DBUPlayer;
import net.kyori.adventure.text.Component;

import java.util.UUID;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class VelocityPlayer implements DBUPlayer {

    private final Player player;

    /**
     * Instantiates a new Velocity player.
     *
     * @param player the player
     */
    public VelocityPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void kick(String reason) {
        player.disconnect(Component.text(reason));
    }

    @Override
    public void sendMessageRaw(String message) {
        player.sendMessage(Component.text(message));
    }

    @Override
    public boolean isConsole() {
        return true;
    }

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getUsername();
    }

    @Override
    public long sendMessage(String message) {
        player.sendMessage(Component.text(DBUtil.getINSTANCE().getMcMessagesFileHandler().getPrefix() + message));
        return -1;
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void sendComponent(Component component) {
        player.sendMessage(component);
    }
}
