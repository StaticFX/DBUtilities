package de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.CommandSender;
import de.staticred.dbv2.util.BotHelper;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.event.Event;

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
            sender.sendMessage("Use: dbperms addinherit <role> <role>");
            return;
        }




        String roleString = args[1].substring(3, args[1].length() - 1);

        String roleInheritString = args[2].substring(3, args[2].length() - 1);

        long roleID;
        long inheritID;

        try {
            roleID = Long.parseLong(roleString);
            inheritID = Long.parseLong(roleInheritString);
        } catch (NumberFormatException e) {
            sender.sendMessage("Can't convert given id into long");
            return;
        }

        Role role = BotHelper.jda.getRoleById(roleID);
        Role roleInherit = BotHelper.jda.getRoleById(inheritID);

        if (role == null || roleInherit == null) {
            //should never come to here
            sender.sendMessage("Role could not be found");
            return;
        }
        DBUtil.getINSTANCE().getPermissionHandler().addInherit(roleID, inheritID);

        sender.sendMessage("Add inhert: " + roleInherit.getAsMention() + " to role: " + role.getAsMention());
    }

}
