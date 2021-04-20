package de.staticred.dbv2.player;

import com.velocitypowered.api.proxy.ConsoleCommandSource;
import de.staticred.dbv2.DBUtil;
import net.kyori.adventure.text.Component;

import java.util.UUID;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class VelocityConsole implements DBUPlayer {

    private ConsoleCommandSource console;


    /**
     * Instantiates a new Velocity console.
     *
     * @param console the console
     */
    public VelocityConsole(ConsoleCommandSource console) {
        this.console = console;
    }

    @Override
    public void kick(String reason) {
    }

    @Override
    public void sendMessageRaw(String message) {
        console.sendMessage(Component.text(DBUtil.getINSTANCE().getMcMessagesFileHandler().getPrefix() + message));
    }

    @Override
    public UUID getUUID() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void sendMessage(String message) {
        console.sendMessage(Component.text(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return console.hasPermission(permission);
    }
}
