package de.staticred.dbv2.commands.mccommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.commands.util.DBUCommand;
import de.staticred.dbv2.player.DBUPlayer;

/**
 * Info command returning general information
 *
 * @author Devin
 * @version 1.0.0
 */
public class InfoDBUCommand implements DBUCommand {

    private static final String NAME = "Info";
    private static final String PERMISSION = "mc.cmd.info";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getPermission() {
        return PERMISSION;
    }

    @Override
    public void execute(DBUPlayer player, String[] args) {
        player.sendMessage("§aRunning " + DBUtil.PLUGIN_NAME + " version: " + DBUtil.VERSION);
    }
}
