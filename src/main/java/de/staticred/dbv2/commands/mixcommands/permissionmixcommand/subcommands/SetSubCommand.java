package de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.permission.PermissionHandler;
import de.staticred.dbv2.player.CommandSender;
import de.staticred.dbv2.util.BotHelper;
import de.staticred.dbv2.util.RoleBuilder;
import net.dv8tion.jda.api.entities.Role;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class SetSubCommand {

    private final static String PERMISSION = "db.cmd.mix.dbperms.set";

    public SetSubCommand() {
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("You don't have permission for this action!");
            return;
        }

        if (args.length != 4) {
            sender.sendMessage("Use: dbperms set <role> <permission> <state>");
            return;
        }

        String roleString = args[1];

        String permission = args[2];

        String state = args[3];

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

        PermissionHandler handler = DBUtil.getINSTANCE().getPermissionHandler();

        boolean stateB;

        if (state.equalsIgnoreCase("true")) {
            stateB = true;
        } else if (state.equalsIgnoreCase("false")) {
            stateB = false;
        } else {
            sender.sendMessage("Use: dbperms set <role> <permission> <state>");
            return;
        }

        handler.setEnabledState(stateB, roleID, permission);
        sender.sendMessage("Set permission **" + permission + "** in role " + role.getAsMention() + " to **" + state + "**");
    }

}
