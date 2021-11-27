package de.staticred.dbv2bungeecord.events;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.annotations.Side;
import de.staticred.dbv2.events.MessageEvent;
import de.staticred.dbv2bungeecord.player.BungeeConsole;
import de.staticred.dbv2bungeecord.player.BungeePlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Will catch every command on the bungeecord side
 * and send them into the commandmanager so it can handle the input and process them further
 *
 *
 * @author Devin
 * @version 1.0.0
 */
public class CommandEvent implements Listener {

    @Side(proxy = Side.Proxy.BUNGEECORD)
    @EventHandler
    public void onChat(ChatEvent event) {

        String message = event.getMessage();

        if (event.getSender() instanceof ProxiedPlayer) {
            //player send the message
            if (message.startsWith("/")) {
                message = message.substring(1);

                if (DBUtil.getINSTANCE().getCommandManager().doesCommandExist(message) && !message.startsWith("dbu")) {
                    event.setCancelled(true);
                    if (event.getSender() instanceof ProxiedPlayer) {
                        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
                        BungeePlayer bungeePlayer = new BungeePlayer(player);
                        DBUtil.getINSTANCE().getCommandManager().handleMCInput(bungeePlayer, message);
                    }
                }
            } else {
                //not a command message
                MessageEvent messageEvent = new MessageEvent(new BungeePlayer((ProxiedPlayer) event.getSender()), event.getMessage(), false, Side.Proxy.BUNGEECORD);

                DBUtil.getINSTANCE().getEventManager().fireEvent(messageEvent);

                if (messageEvent.isCanceled())
                    event.setCancelled(true);

            }
        } else {
            //not a player send the message
            //Console
            CommandSender console = ProxyServer.getInstance().getConsole();
            BungeeConsole bungeeConsole = new BungeeConsole(console);
            DBUtil.getINSTANCE().getCommandManager().handleMCInput(bungeeConsole, message);
        }
    }
}
