package de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.CommandSender;
import de.staticred.dbv2.discord.util.RoleBuilder;
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

        String roleString = args[1];

        Role role = RoleBuilder.buildRoleFromMessage(roleString);

        if (role == null) {
            sender.sendMessage("Role could not be found");
            return;
        }

        long roleID = role.getIdLong();


        List<Role> inheritRoles = DBUtil.getINSTANCE().getPermissionHandler().getInheritRoles(roleID);

        Map<String, Boolean> permissions = DBUtil.getINSTANCE().getPermissionHandler().getPermission(roleID, false);

        StringBuilder sb = new StringBuilder();

        sb.append("**Information about: **").append(role.getAsMention()).append("\n");

        sb.append(System.lineSeparator());
        sb.append("Permissions: ").append(System.lineSeparator());
        for (String permission : permissions.keySet()) {

            boolean state = permissions.get(permission);

            if (state)
                sb.append("  **").append(state).append("**  --> ").append(permission).append(System.lineSeparator());
            else
                sb.append("  **").append(state).append("** --> ").append(permission).append(System.lineSeparator());

        }
        sb.append(System.lineSeparator());
        sb.append("Inheriting from: ").append(System.lineSeparator());

        for (Role inheriting : inheritRoles) {
            sb.append("  -").append(inheriting.getAsMention()).append("\n");
        }

        sender.sendMessage(sb.toString());

    }
}
