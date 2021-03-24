package de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.CommandSender;
import de.staticred.dbv2.util.BotHelper;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.Map;

/**
 * Lists all commands
 *
 * @author Devin
 * @version 1.0.0
 */
public class ListSubCommand {

    private final static String PERMISSION = "db.cmd.mix.dbperms.list";

    public ListSubCommand() {
    }

    public void execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("You don't have permission for this action!");
            return;
        }

        if (args.length != 2) {
            sender.sendMessage("Use: dbperms list <role>");
            return;
        }

        String roleString = args[1].substring(3, args[1].length() - 1);

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


        List<Role> inheritRoles = DBUtil.getINSTANCE().getPermissionHandler().getInheritRoles(roleID);

        Map<String, Boolean> permissions = DBUtil.getINSTANCE().getPermissionHandler().getPermission(roleID, false);

        StringBuilder sb = new StringBuilder();

        sb.append("Permission found for: " + role.getAsMention() + "\n");


        for (String permission : permissions.keySet()) {
            sb.append("Permission: " + permission + " enabled: " + permissions.get(permission) + "\n");
        }

        sb.append("Inheriting from: ");

        for (Role inheriting : inheritRoles) {
            sb.append(inheriting.getAsMention() + "\n");
        }

        sender.sendMessage(sb.toString());

    }
}
