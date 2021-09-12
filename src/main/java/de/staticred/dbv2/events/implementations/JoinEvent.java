package de.staticred.dbv2.events.implementations;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.annotations.EventHandler;
import de.staticred.dbv2.events.util.Listener;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class JoinEvent implements Listener {


    @EventHandler
    public void onJoin(de.staticred.dbv2.events.JoinEvent event) {
        if (!DBUtil.getINSTANCE().getUpdateChecker().isLatest()) {
            if (event.getPlayer().hasPermission("dbu.alert")) {
                event.getPlayer().sendMessage("&aYou are not on the latest version. Download it over here: https://www.spigotmc.org/resources/dbutil-strong-discord-mc-api.90724/");
            }
        }
    }

}
