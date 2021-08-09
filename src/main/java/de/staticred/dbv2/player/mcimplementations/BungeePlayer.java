package de.staticred.dbv2.player.mcimplementations;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.proxies.bungeecord.DBVerifierPlugin;
import de.staticred.dbv2.util.DoubleOptional;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.kyori.adventure.text.Component;
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
    public DoubleOptional<Message, InteractionHook> sendMessage(String message) {
        bungeeCordPlayer.sendMessage(new TextComponent(DBUtil.getINSTANCE().getMcMessagesFileHandler().getPrefix() + message.replaceAll("&", "§")));
        return null;
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


    @Override
    public void sendComponent(Component component) {
        DBVerifierPlugin.getInstance().getBungeeAudiences().player(bungeeCordPlayer).sendMessage(component);
    }
}
