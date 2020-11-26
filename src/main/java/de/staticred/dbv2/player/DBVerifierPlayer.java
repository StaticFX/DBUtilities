package de.staticred.dbv2.player;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;

/**
 * DBVerifierPlayer models a player which can be a bukkit or bungeecord player, used for independent commands/event
 *
 * @author Devin
 */

public class DBVerifierPlayer {

    private Player bukkitPlayer = null;
    private ProxiedPlayer bungeeCordPlayer = null;


    public DBVerifierPlayer(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
    }

    public DBVerifierPlayer(ProxiedPlayer bungeeCordPlayer) {
        this.bungeeCordPlayer = bungeeCordPlayer;
    }

    private void sendMessage(String message) {

        if(bukkitPlayer != null) bukkitPlayer.sendMessage(message);
        if(bungeeCordPlayer != null) bungeeCordPlayer.sendMessage(new TextComponent(message));

    }

}
