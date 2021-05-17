package de.staticred.dbv2.commands.mixcommands.botcommand.subcommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.CommandSender;
import de.staticred.dbv2.discord.util.BotHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class InfoCommand {

    public InfoCommand() {
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("dbu.cmd.bot.info")) {
            sender.sendMessage("No permission!");
            return;
        }

        long botStarted = BotHelper.botstartup;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DBUtil.TIME_PATTERN);

        String date = simpleDateFormat.format(new Date(botStarted));

        sender.sendMessage("Bot was started @" + date);
    }


}
