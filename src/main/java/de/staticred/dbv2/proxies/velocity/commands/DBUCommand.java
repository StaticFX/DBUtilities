package de.staticred.dbv2.proxies.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.player.mcimplementations.VelocityConsole;
import de.staticred.dbv2.player.mcimplementations.VelocityPlayer;
import net.kyori.adventure.text.Component;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class DBUCommand implements SimpleCommand {


    @Override
    public void execute(Invocation invocation) {

        String[] args  = invocation.arguments();

        CommandSource sender = invocation.source();

        StringBuilder cmd = new StringBuilder();

        if (args.length == 0) {
            sender.sendMessage((Component.text("§cUse /dbu <command>")));
            return;
        }


        for (String arg : args) {
            if (cmd.length() == 0)
                cmd = new StringBuilder(arg);
            else
                cmd.append(" ").append(arg);
        }

        DBUPlayer dbuPlayer;

        if (sender instanceof Player) {
            dbuPlayer = new VelocityPlayer((Player) sender);
        } else {
            dbuPlayer = new VelocityConsole((ConsoleCommandSource) sender);
        }

        if (!DBUtil.getINSTANCE().getCommandManager().doesCommandExist(args[0])) {
            sender.sendMessage(Component.text("§cCommand not found"));
            return;
        }

        DBUtil.getINSTANCE().getCommandManager().handleMCInput(dbuPlayer, cmd.toString());
    }


    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("dbu.cmd.dbu");
    }
}
