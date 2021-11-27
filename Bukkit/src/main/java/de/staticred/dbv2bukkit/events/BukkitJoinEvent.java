package de.staticred.dbv2bukkit.events;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.annotations.Side;
import de.staticred.dbv2.events.JoinEvent;
import de.staticred.dbv2bukkit.player.BukkitPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class BukkitJoinEvent implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        DBUtil.getINSTANCE().getEventManager().fireEvent(new JoinEvent(new BukkitPlayer(event.getPlayer()), Side.Proxy.BUKKIT));
    }

}
