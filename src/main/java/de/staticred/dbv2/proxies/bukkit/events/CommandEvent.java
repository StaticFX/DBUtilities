package de.staticred.dbv2.proxies.bukkit.events;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.annotations.Side;
import de.staticred.dbv2.player.mcimplementations.BukkitPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Event when a user executes a command on the bukkit site
 *
 * @author Devin
 * @version 1.0.0
 */
public class CommandEvent implements Listener {

    @Side(proxy = Side.Proxy.BUKKIT)
    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent event) {
        String cmd = event.getMessage();

        cmd = cmd.substring(1);

        if (DBUtil.getINSTANCE().getCommandManager().doesCommandExist(cmd)) {
            event.setCancelled(true);
            DBUtil.getINSTANCE().getCommandManager().handleMCInput(new BukkitPlayer(event.getPlayer()), cmd);
            return;
        }
    }

}
