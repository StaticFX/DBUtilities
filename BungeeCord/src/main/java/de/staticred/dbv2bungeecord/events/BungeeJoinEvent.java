package de.staticred.dbv2bungeecord.events;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.annotations.Side;
import de.staticred.dbv2.events.JoinEvent;
import de.staticred.dbv2bungeecord.player.BungeePlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class BungeeJoinEvent implements Listener {

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        DBUtil.getINSTANCE().getEventManager().fireEvent(new JoinEvent(new BungeePlayer( event.getPlayer()), Side.Proxy.BUNGEECORD));
    }

}
