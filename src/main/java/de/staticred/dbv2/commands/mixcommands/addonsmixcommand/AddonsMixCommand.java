package de.staticred.dbv2.commands.mixcommands.addonsmixcommand;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.addon.Addon;
import de.staticred.dbv2.commands.util.MixCommand;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.player.DiscordSender;

import java.util.Collection;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class AddonsMixCommand implements MixCommand {

    public static String NAME = "Addons";
    public static String PERMISSION = "dbu.cmd.addons";
    public static String PREFIX = DBUtil.getINSTANCE().getConfigFileManager().getPrefix();


    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public String getPermission() {
        return PERMISSION;
    }

    @Override
    public void executeDC(DiscordSender sender, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("No permission!");
            return;
        }

        Collection<Addon> addons = DBUtil.getINSTANCE().getAddons();

        String addonsString = "";

        for (Addon addon : addons) {
            if (!addonsString.isEmpty())
                addonsString = String.join(", ", addonsString, addon.getName());
            else
                addonsString = addon.getName();
        }
        sender.sendMessage(addonsString);
    }

    @Override
    public void executeMC(DBUPlayer sender, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("No permission!");
            return;
        }

        Collection<Addon> addons = DBUtil.getINSTANCE().getAddons();

        String addonsString = "";

        for (Addon addon : addons) {
            if (!addonsString.isEmpty())
                addonsString = String.join(", ", addonsString, "§a" +  addon.getName());
            else
                addonsString = "§a" +  addon.getName();
        }
        sender.sendMessage(addonsString);
    }
}
