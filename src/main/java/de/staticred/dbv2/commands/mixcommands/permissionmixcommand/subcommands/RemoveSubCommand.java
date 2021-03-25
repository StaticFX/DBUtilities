package de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.CommandSender;
import de.staticred.dbv2.util.BotHelper;
import net.dv8tion.jda.api.entities.Role;

/**
 * Removes permission from a group
 *
 */
public class RemoveSubCommand {

    private final static String PERMISSION = "db.cmd.mix.dbperms.remove";

    /**
     * constructor.
     */
    public RemoveSubCommand() {
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("You don't have permission for this action!");
            return;
        }

        if (args.length != 3) {
            sender.sendMessage("Use: dbperms remove <role> <permission>");
            return;
        }

        String roleString = args[1].substring(3, args[1].length() - 1);

        String permission = args[2];

        long roleID;

        try {
            roleID = Long.parseLong(roleString);
        } catch (NumberFormatException e) {
            sender.sendMessage("Can't convert given id into long");
            return;
        }

        Role role = BotHelper.jda.getRoleById(roleID);

        if (role == null) {
            //should never come to here
            sender.sendMessage("Role could not be found");
            return;
        }
        DBUtil.getINSTANCE().getPermissionHandler().removePermission(roleID, permission);

        sender.sendMessage("Removed permission:**" + permission + "** from role " + role.getAsMention());
    }

}
