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
public class AddonMixCommand implements MixCommand {
    public static String NAME = "Addon";
    public static String PERMISSION = "dbu.cmd.addon";
    public static String PREFIX = "!";

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

        if (args.length != 1) {
            sender.sendMessage("Use /addon <addon>");
            return;
        }

        String addonString = args[0];

        Collection<Addon> addons = DBUtil.getINSTANCE().getAddons();

        if (addons.stream().noneMatch(addon -> addon.getName().equalsIgnoreCase(addonString))) {
            sender.sendMessage("Addon not found");
            return;
        }

        Addon foundAddon = addons.stream().filter(addon -> addon.getName().equalsIgnoreCase(addonString)).findFirst().orElse(null);

        if (foundAddon == null) {
            sender.sendMessage("Addon not found");
            return;
        }

        sender.sendMessage(foundAddon.getName() + " version " + foundAddon.getAddonInfo().getVersion() + " by: " + foundAddon.getAddonInfo().getAuthor());

    }

    @Override
    public void executeMC(DBUPlayer sender, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("§cNo permission!");
            return;
        }

        if (args.length != 1) {
            sender.sendMessage("§cUse /addon <addon>");
            return;
        }

        String addonString = args[0];

        Collection<Addon> addons = DBUtil.getINSTANCE().getAddons();

        if (addons.stream().noneMatch(addon -> addon.getName().equalsIgnoreCase(addonString))) {
            sender.sendMessage("§cAddon not found");
            return;
        }

        Addon foundAddon = addons.stream().filter(addon -> addon.getName().equalsIgnoreCase(addonString)).findFirst().orElse(null);

        if (foundAddon == null) {
            sender.sendMessage("§cAddon not found");
            return;
        }

        sender.sendMessage("§a" + foundAddon.getName() + " version §c" + foundAddon.getAddonInfo().getVersion() + " §aby: §e" + foundAddon.getAddonInfo().getAuthor());
    }
}
