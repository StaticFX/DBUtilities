package de.staticred.dbv2.discord.events;

import de.staticred.dbv2.DBUtil;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class RoleCreateEvent extends ListenerAdapter {

    @Override
    public void onRoleCreate(net.dv8tion.jda.api.events.role.RoleCreateEvent event) {
        DBUtil.getINSTANCE().getPermissionHandler().addRole(event.getRole().getIdLong());
    }

}
