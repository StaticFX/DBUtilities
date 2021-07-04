package de.staticred.dbv2.proxies.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.annotations.Side;
import de.staticred.dbv2.events.JoinEvent;
import de.staticred.dbv2.player.mcimplementations.VelocityPlayer;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class VelocityJoinEvent {

    @Subscribe()
    public void onPlayerChat(PostLoginEvent event) {
        DBUtil.getINSTANCE().getEventManager().fireEvent(new JoinEvent(new VelocityPlayer(event.getPlayer()), Side.Proxy.VELOCITY));
    }

}
