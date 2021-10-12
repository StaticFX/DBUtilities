package de.staticred.dbv2.commands.mixcommands.botcommand;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.commands.mixcommands.botcommand.subcommands.InfoCommand;
import de.staticred.dbv2.commands.mixcommands.botcommand.subcommands.ReloadCommand;
import de.staticred.dbv2.commands.util.MixCommand;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.player.DiscordSender;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class BotMixCommand implements MixCommand {

    public final static String NAME = "bot";
    public final static String PERMISSION = "dbu.cmd.bot";
    public final static String PREFIX = DBUtil.getINSTANCE().getConfigFileManager().getPrefix();

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public String getPermission() {
        return PERMISSION;
    }

    @Override
    public void executeDC(DiscordSender sender, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("No permission!");
            return;
        }

        if (args.length < 1) {
            sender.sendMessage("Use: bot <reload/info>");
            return;
        }

        String subCommand = args[0];

        if (subCommand.equalsIgnoreCase("reload")) {
            new ReloadCommand().execute(sender, args);
            return;
        }
        if (subCommand.equalsIgnoreCase("info")) {
            new InfoCommand().execute(sender, args);
            return;
        }

        sender.sendMessage("Use: bot <reload/info>");
    }

    @Override
    public void executeMC(DBUPlayer sender, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("No permission!");
            return;
        }

        if (args.length < 1) {
            sender.sendMessage("Use: bot <reload/info>");
            return;
        }

        String subCommand = args[0];

        if (subCommand.equalsIgnoreCase("reload")) {
            new ReloadCommand().execute(sender, args);
            return;
        }
        if (subCommand.equalsIgnoreCase("info")) {
            new InfoCommand().execute(sender, args);
            return;
        }

        sender.sendMessage("Use: bot <reload/info>");
    }
}
