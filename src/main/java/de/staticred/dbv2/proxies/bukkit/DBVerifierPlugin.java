package de.staticred.dbv2.proxies.bukkit;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.proxies.bukkit.commands.DBUCommand;
import de.staticred.dbv2.proxies.bukkit.events.BukkitEventManager;
import de.staticred.dbv2.proxies.bukkit.events.BukkitJoinEvent;
import de.staticred.dbv2.proxies.bukkit.events.CommandEvent;
import de.staticred.dbv2.player.mcimplementations.BukkitPlayer;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.util.ConsoleLogger;
import de.staticred.dbv2.util.Mode;
import de.staticred.dbv2.util.Proxy;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.UUID;

public class DBVerifierPlugin extends JavaPlugin implements Proxy {

    private static DBVerifierPlugin INSTANCE;

    private DBUtil dbUtil;

    private BukkitAudiences bukkitAudiences;

    @Override
    public void onEnable() {

        try {
            dbUtil = new DBUtil(this, new BukkitEventManager(), Mode.BUKKIT, new ConsoleLogger(DBUtil.PLUGIN_NAME));
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        bukkitAudiences = BukkitAudiences.create(this);

        INSTANCE = this;
        getCommand("dbu").setExecutor(new DBUCommand());
        DBUtil.getINSTANCE().getLogger().postDebug("Registering bukkit events");
        getServer().getPluginManager().registerEvents(new CommandEvent(), this);
        getServer().getPluginManager().registerEvents(new BukkitJoinEvent(), this);
        DBUtil.getINSTANCE().getLogger().postDebug("Registered bukkit events");
    }

    @Override
    public void onDisable() {
        dbUtil.shutDown();
    }

    @Override
    public void executeConsoleCommand(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    @Override
    public DBUPlayer getOnlinePlayer(String name) {
        Player player = Bukkit.getPlayer(name);

        return player != null ? new BukkitPlayer(player) : null;
    }

    public static DBVerifierPlugin getInstance() {
        return INSTANCE;
    }

    public BukkitAudiences getBukkitAudiences() {
        return bukkitAudiences;
    }

    @Override
    public DBUPlayer getPlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);

        return player != null ? new BukkitPlayer(player) : null;
    }

}
