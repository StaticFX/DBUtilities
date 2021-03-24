package de.staticred.dbv2.discord.events;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.permission.PermissionHandler;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class RoleDeleteEvent extends ListenerAdapter {


    @Override
    public void onRoleDelete(net.dv8tion.jda.api.events.role.RoleDeleteEvent event) {
        DBUtil.getINSTANCE().getPermissionHandler().removeRole(event.getRole().getIdLong());
    }

}
