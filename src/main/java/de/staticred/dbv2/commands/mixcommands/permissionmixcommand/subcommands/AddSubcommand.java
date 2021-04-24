package de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.CommandSender;
import de.staticred.dbv2.util.BotHelper;
import de.staticred.dbv2.util.RoleBuilder;
import net.dv8tion.jda.api.entities.Role;

/**
 * Adds permission to a group
 *
 */
public class    AddSubcommand {

    private final static String PERMISSION = "db.cmd.mix.dbperms.add";

    /**
     * constructor.
     */
    public AddSubcommand() {
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("You don't have permission for this action!");
            return;
        }

        if (args.length != 3) {
            sender.sendMessage("Use: dbperms add <role> <permission>");
            return;
        }

        String roleString = args[1];

        String permission = args[2];

        long roleID;

        try {
            roleID = Long.parseLong(roleString);
        } catch (NumberFormatException e) {
            sender.sendMessage("Can't convert given id into long");
            return;
        }

        Role role = RoleBuilder.buildRoleFromMessage(roleString);


        if (role == null) {
            //should never come to here
            sender.sendMessage("Role could not be found");
            return;
        }

        DBUtil.getINSTANCE().getPermissionHandler().setPermission(roleID, permission);

        sender.sendMessage("Add permission **" + permission + "** to role " + role.getAsMention());
    }
}
