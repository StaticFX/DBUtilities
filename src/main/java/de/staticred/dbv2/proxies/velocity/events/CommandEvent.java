package de.staticred.dbv2.proxies.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.player.VelocityConsole;
import de.staticred.dbv2.player.VelocityPlayer;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class CommandEvent {

    @Subscribe
    public void onCommand(CommandExecuteEvent event) {

        String cmd = event.getCommand();

        DBUPlayer sender;


        if (event.getCommandSource() instanceof Player) {
            Player player = (Player) event.getCommandSource();
            sender = new VelocityPlayer(player);
        } else {
            ConsoleCommandSource console = (ConsoleCommandSource) event.getCommandSource();
            sender = new VelocityConsole(console);
        }

        DBUtil.getINSTANCE().getCommandManager().handleMCInput(sender, event.getCommand());
    }

}
