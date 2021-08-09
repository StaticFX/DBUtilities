package de.staticred.dbv2.player.mcimplementations;

import com.velocitypowered.api.proxy.ConsoleCommandSource;
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
        console.sendMessage(Component.text(DBUtil.getINSTANCE().getMcMessagesFileHandler().getPrefix() + message.replaceAll("&", "§")));

    }

    @Override
    public UUID getUUID() {
        return null;
    }

    @Override
    public boolean isConsole() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public DoubleOptional<Message, InteractionHook> sendMessage(String message) {
        console.sendMessage(Component.text(message));
        return null;
    }

    @Override
    public boolean hasPermission(String permission) {
        return console.hasPermission(permission);
    }

    @Override
    public void sendComponent(Component component) {
        console.sendMessage(component);
    }
}
