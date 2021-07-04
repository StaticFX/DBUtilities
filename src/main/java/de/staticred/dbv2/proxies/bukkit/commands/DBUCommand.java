package de.staticred.dbv2.proxies.bukkit.commands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.mcimplementations.BukkitConsole;
import de.staticred.dbv2.player.mcimplementations.BukkitPlayer;
import de.staticred.dbv2.player.DBUPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class DBUCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        StringBuilder cmd = new StringBuilder();

        if (args.length == 0) {
            sender.sendMessage(("§cUse /dbu <command>"));
            return false;
        }


        for (String arg : args) {
            if (cmd.length() == 0)
                cmd = new StringBuilder(arg);
            else
                cmd.append(" ").append(arg);
        }

        DBUPlayer dbuPlayer;

        if (sender instanceof Player) {
            dbuPlayer = new BukkitPlayer((Player) sender);
        } else {
            dbuPlayer = new BukkitConsole(sender);
        }

        if (!DBUtil.getINSTANCE().getCommandManager().doesCommandExist(args[0])) {
            sender.sendMessage(("§cCommand not found"));
            return false;
        }

        DBUtil.getINSTANCE().getCommandManager().handleMCInput(dbuPlayer, cmd.toString());
        return true;
    }
}
