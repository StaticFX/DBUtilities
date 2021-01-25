package de.staticred.dbv2.bukkit.events;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.annotations.Side;
import de.staticred.dbv2.player.BukkitConsole;
import de.staticred.dbv2.player.BukkitPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

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

        System.out.println("Command in from bukkit: " + cmd);

        if (DBUtil.getINSTANCE().getCommandManager().doesCommandExist(cmd)) {
            DBUtil.getINSTANCE().getCommandManager().handleMCInput(new BukkitPlayer(event.getPlayer()), cmd);
            System.out.println("here");
            event.setCancelled(true);
        }
    }

    @Side(proxy = Side.Proxy.BUKKIT)
    @EventHandler
    public void onConsoleCMD(ServerCommandEvent event) {
        String cmd = event.getCommand();
        if (DBUtil.getINSTANCE().getCommandManager().doesCommandExist(cmd)) {
            DBUtil.getINSTANCE().getCommandManager().handleMCInput(new BukkitConsole(event.getSender()), cmd);
            event.setCancelled(true);
        }
    }

}
