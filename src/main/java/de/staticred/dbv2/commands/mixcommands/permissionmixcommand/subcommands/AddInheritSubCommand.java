package de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.CommandSender;
import de.staticred.dbv2.discord.util.RoleBuilder;
import net.dv8tion.jda.api.entities.Role;

/**
 * Adds inherit to the role
 *
 * @author Devin
 * @version 1.0.0
 */
public class AddInheritSubCommand {

    private final static String PERMISSION = "db.cmd.mix.dbperms.addinherit";

    /**
     * constructor.
     */
    public AddInheritSubCommand() {
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("You don't have permission for this action!");
            return;
        }

        if (args.length != 3) {
            sender.sendMessage("Use: dbperms addinherit <role> <fromRole>");
            return;
        }

        String roleString = args[1];

        String roleInheritString = args[2];

        long roleID;
        long inheritID;

        try {
            roleID = Long.parseLong(roleString);
            inheritID = Long.parseLong(roleInheritString);
        } catch (NumberFormatException e) {
            sender.sendMessage("Can't convert given id into long");
            return;
        }

        Role role = RoleBuilder.buildRoleFromMessage(roleString);

        Role roleInherit = RoleBuilder.buildRoleFromMessage(roleInheritString);


        if (role == null || roleInherit == null) {
            //should never come to here
            sender.sendMessage("Role could not be found");
            return;
        }
        DBUtil.getINSTANCE().getPermissionHandler().addInherit(roleID, inheritID);

        sender.sendMessage("Role " + role.getAsMention() + " inherits now from " + roleInherit.getAsMention());
    }

}
