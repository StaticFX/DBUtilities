package de.staticred.dbv2.commands.mixcommands.commandmix;

import de.staticred.dbv2.commands.util.MixCommand;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.player.DiscordSender;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class CMDCommand implements MixCommand {

    private final static String NAME = "cmd";
    private final static String PERMISSION = "dbu.cmd.mix.cmd";

    public CMDCommand() {
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public void executeDC(DiscordSender sender, String[] args) {

    }

    @Override
    public void executeMC(DBUPlayer sender, String[] args) {

    }
}
