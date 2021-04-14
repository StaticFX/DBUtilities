package de.staticred.dbv2.bukkit.events;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.annotations.Side;
import de.staticred.dbv2.events.MessageEvent;
import de.staticred.dbv2.player.BukkitPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BukkitMessageEvent implements Listener {

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        MessageEvent messageEvent = new MessageEvent(new BukkitPlayer(event.getPlayer()), event.getMessage(), false, Side.Proxy.BUKKIT);

        DBUtil.getINSTANCE().getEventManager().fireEvent(messageEvent);

        if (messageEvent.isCanceled()) {
            event.setCancelled(true);
        }
    }

}
