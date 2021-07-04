package de.staticred.dbv2.proxies.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.annotations.Side;
import de.staticred.dbv2.events.MessageEvent;
import de.staticred.dbv2.player.mcimplementations.VelocityPlayer;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class VelocityChatEvent  {

    @Subscribe
    public void onChat(PlayerChatEvent event) {
        MessageEvent messageEvent = new MessageEvent(new VelocityPlayer(event.getPlayer()), event.getMessage(), false, Side.Proxy.VELOCITY);
        DBUtil.getINSTANCE().getEventManager().fireEvent(messageEvent);

        if (messageEvent.isCanceled())
            event.setResult(PlayerChatEvent.ChatResult.denied());

    }

}
