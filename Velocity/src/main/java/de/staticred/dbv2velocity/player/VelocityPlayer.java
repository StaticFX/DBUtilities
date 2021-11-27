package de.staticred.dbv2velocity.player;


import com.velocitypowered.api.proxy.Player;
import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.util.DoubleOptional;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.InteractionHook;
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
    public DoubleOptional<Message, InteractionHook> sendMessage(String message) {
        player.sendMessage(Component.text(DBUtil.getINSTANCE().getMcMessagesFileHandler().getPrefix() + message.replaceAll("&", "§")));
        return null;
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
