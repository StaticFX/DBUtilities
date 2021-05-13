package de.staticred.dbv2.commands.mixcommands.botcommand.subcommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.constants.FileConstants;
import de.staticred.dbv2.player.CommandSender;
import de.staticred.dbv2.util.BotHelper;

import javax.security.auth.login.LoginException;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class ReloadCommand {

    public ReloadCommand() {
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("dbu.cmd.bot.reload")) {
            sender.sendMessage("No permission!");
            return;
        }

        BotHelper.jda.shutdownNow();
        try {
            BotHelper.startBot(DBUtil.getINSTANCE().getConfigFileManager().getConfigObject().getString(FileConstants.TOKEN));
        } catch (LoginException e) {
            e.printStackTrace();
            sender.sendMessage(e.getMessage());
            return;
        }

        sender.sendMessage("Bot restarted!");
    }



}
