package de.staticred.dbv2.commands.mixcommands.permissionmixcommand;

import de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands.*;
import de.staticred.dbv2.commands.util.MixCommand;
import de.staticred.dbv2.discord.util.Embed;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.player.DiscordSender;
import de.staticred.dbv2.player.MemberSender;

import java.awt.*;


/**
 * Models a command to interface with the permission system
 *
 * @author Devin
 * @version 1.0.0
 */
public class PermissionMixCommand implements MixCommand {

    public static final String NAME = "dbperms";


    public static final String PERMISSION = "dbu.cmd.mix.dbperms";


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
        return PERMISSION;
    }

    @Override
    public void executeDC(DiscordSender sender, String[] args) {

        StringBuilder sb = new StringBuilder();

        if (args.length < 1) {
            Embed embed;
            embed = new Embed("dbperms add <role> <permission> - adds permission\n" +
                    "dbperms list <role> - displays the permission\n" +
                    "dbperms remove <role> <permission> - removes the permission\n" +
                    "dbperms addInherit <role> <role> - adds inherit role\n" +
                    "dbperms removeInherit <role> <role> - removes inherit role\n" +
                    "dbperms set <role> <permission> <state> - set enabled state\n" +
                    "dbperms backup - saves current permissiondata as file in the backup folder\n"
                    , "DBUtil Permission System", true, Color.ORANGE, sender.getMember().getUser().getAvatarUrl());
            sender.sendEmbed(embed.build());
            return;
        }

        switch (args[0].toUpperCase()) {

            case "ADD": {
                new AddSubcommand().execute(sender, args);
                return;
            }
            case "LIST": {
                new ListSubCommand().execute(sender, args);
                return;
            }
            case "REMOVE": {
                new RemoveSubCommand().execute(sender, args);
                return;
            }
            case "ADDINHERIT": {
                new AddInheritSubCommand().execute(sender, args);
                return;
            }
            case "REMOVEINHERIT": {
                new RemoveInheritSubCommand().execute(sender, args);
                return;
            }
            case "SET": {
                new SetSubCommand().execute(sender, args);
                return;
            }
            case "BACKUP": {
                new BackUpSubCommand().execute(sender, args);
                return;
            }
        }

        sb.append("dbperms add <role> <permission> - adds permission\n");
        sb.append("dbperms list <role> - displays the permission\n");
        sb.append("dbperms remove <role> <permission> - removes the permission\n");
        sb.append("dbperms addInherit <role> <role> - adds inherit role\n");
        sb.append("dbperms removeInherit <role> <role> - removes inherit role\n");
        sb.append("dbperms set <role> <permission> <state> - set enabled state\n");
        sb.append("dbperms backup - saves current permissiondata as file in the backup folder\n");
        Embed embed = new Embed(sb.toString(), "DBUtil Permission System", true, Color.ORANGE, sender.getMember().getUser().getAvatarUrl());
        sender.sendEmbed(embed.build());
    }


    @Override
    public void executeMC(DBUPlayer sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("dbperms add <role> <permission> - adds permission\n");
            sender.sendMessage("dbperms list <role> - displays the permission\n");
            sender.sendMessage("dbperms remove <role> <permission> - removes the permission\n");
            sender.sendMessage("dbperms addInherit <role> <role> - adds inherit role\n");
            sender.sendMessage("dbperms removeInherit <role> <role> - removes inherit role\n");
            sender.sendMessage("dbperms set <role> <permission> <state> - set enabled state\n");
            sender.sendMessage("dbperms backup - saves current permissiondata as file in the backup folder\n");
            return;
        }

        switch (args[0].toUpperCase()) {

            case "ADD": {
                new AddSubcommand().execute(sender, args);
                return;
            }
            case "LIST": {
                new ListSubCommand().execute(sender, args);
                return;
            }
            case "REMOVE": {
                new RemoveSubCommand().execute(sender, args);
                return;
            }
            case "ADDINHERIT": {
                new AddInheritSubCommand().execute(sender, args);
                return;
            }
            case "REMOVEINHERIT": {
                new RemoveInheritSubCommand().execute(sender, args);
                return;
            }
            case "SET": {
                new SetSubCommand().execute(sender, args);
                return;
            }
            case "BACKUP": {
                new BackUpSubCommand().execute(sender, args);
                return;
            }
        }

        sender.sendMessage("dbperms add <role> <permission> - adds permission\n");
        sender.sendMessage("dbperms list <role> - displays the permission\n");
        sender.sendMessage("dbperms remove <role> <permission> - removes the permission\n");
        sender.sendMessage("dbperms addInherit <role> <role> - adds inherit role\n");
        sender.sendMessage("dbperms removeInherit <role> <role> - removes inherit role\n");
        sender.sendMessage("dbperms set <role> <permission> <state> - set enabled state\n");
        sender.sendMessage("dbperms backup - saves current permissiondata as file in the backup folder\n");
    }
}
