package de.staticred.dbv2.bungeecord.commands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.BungeeConsole;
import de.staticred.dbv2.player.BungeePlayer;
import de.staticred.dbv2.player.DBUPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class DBCommand extends Command {

    public DBCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        StringBuilder cmd = new StringBuilder();

        if (args.length == 0) {
            sender.sendMessage(new TextComponent("§cUse /dbu <command>"));
            return;
        }


        for (String arg : args) {
            if (cmd.length() == 0)
                cmd = new StringBuilder(arg);
            else
                cmd.append(" ").append(arg);
        }

        DBUPlayer dbuPlayer;

        if (sender instanceof ProxiedPlayer) {
            dbuPlayer = new BungeePlayer((ProxiedPlayer) sender);
        } else {
            dbuPlayer = new BungeeConsole(sender);
        }

        if (!DBUtil.getINSTANCE().getCommandManager().doesCommandExist(args[0])) {
            sender.sendMessage(new TextComponent("§cCommand not found"));
            return;
        }

        DBUtil.getINSTANCE().getCommandManager().handleMCInput(dbuPlayer, cmd.toString());
    }
}
