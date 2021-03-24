package de.staticred.dbv2.commands.mixcommands.permissionmixcommand;

import de.staticred.dbv2.commands.util.MixCommand;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.player.MemberSender;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class RandomMixCommand implements MixCommand {

    public RandomMixCommand() {
    }

    @Override
    public String getName() {
        return "Random command";
    }

    @Override
    public String getPrefix() {
        return "!";
    }

    @Override
    public String getPermission() {
        return "command.permission";
    }

    @Override
    public void executeDC(MemberSender sender, String[] args) {

    }

    @Override
    public void executeMC(DBUPlayer sender, String[] args) {

    }
}
