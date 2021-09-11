package de.staticred.dbv2.commands.mccommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.commands.util.DBUCommand;
import de.staticred.dbv2.commands.util.MixCommand;
import de.staticred.dbv2.player.DBUPlayer;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class HelpMCCommand implements DBUCommand {


    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "mc.cmd.help";
    }

    @Override
    public void execute(DBUPlayer player, String[] args) {
        for (DBUCommand command : DBUtil.getINSTANCE().getCommandManager().getCopyOfRegisteredMCCommands()) {
            player.sendMessage("/" + command.getName());
        }

        for (MixCommand command : DBUtil.getINSTANCE().getCommandManager().getCopyOfRegisteredMixCommands()) {
            player.sendMessage("/" + command.getPrefix() + command.getName());
        }
    }
}
