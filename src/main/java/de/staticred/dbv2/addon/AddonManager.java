package de.staticred.dbv2.addon;

import de.staticred.dbv2.DBUtil;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Util class helping loading all the addons and managing them
 *
 * @author Devin
 * @version 1.0.0
 */
public class AddonManager {

    private final DBUtil session;
    private File addonDirectory;

    public AddonManager(DBUtil session, File dataFolder) {
        this.session = session;
        addonDirectory = loadAddonDirectory(dataFolder);
    }

    private File loadAddonDirectory(File dataFolder) {
        File addonDirectory = new File(dataFolder.getAbsolutePath() + "/addons");
        addonDirectory.mkdirs();
        return addonDirectory;
    }


    /**
     * loads all addons in the given directory
     * @return list off all addons
     */
    public List<Addon> loadAddons() {
        if (!addonDirectory.isDirectory())
            throw new IllegalArgumentException("File must be directory");

        List<Addon> addons = new ArrayList<>();

        for (File file : addonDirectory.listFiles()) {

            if (file.getName().endsWith(".jar")) {
                //probably a .jar
                URL url;
                try {
                    url = file.toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    continue;
                }
                URL[] urls = new URL[]{url};

                // Create a new class loader with the directory
                ClassLoader cl = new URLClassLoader(urls, this.getClass().getClassLoader());

                InputStream stream = cl.getResourceAsStream("addon.yml");

                if (stream == null) {
                    DBUtil.getINSTANCE().getLogger().postError("Addon.yml was not found for addon: " + file.getName());
                    continue;
                }

                YamlFile yamlConfig;
                try {
                    yamlConfig = new YamlFile();
                    yamlConfig.load(stream);
                } catch (InvalidConfigurationException | IOException e) {
                    DBUtil.getINSTANCE().getLogger().postError("Can't load addon.yml for addon: " + file.getName());
                    e.printStackTrace();
                    continue;
                }

                String main = yamlConfig.getString("main");
                String name = yamlConfig.getString("name");
                String author = yamlConfig.getString("author");
                String version = yamlConfig.getString("version");

                if (version == null)
                    version = "N/A";

                if (main == null) {
                    DBUtil.getINSTANCE().getLogger().postError("Can't find main class for addon: " + file.getName());
                    continue;
                }

                if (name == null) {
                    DBUtil.getINSTANCE().getLogger().postError("Can't find name for addon: " + file.getName());
                    continue;
                }

                Class<?> cls;

                try {
                     cls = cl.loadClass(main);
                } catch (ClassNotFoundException e) {
                    DBUtil.getINSTANCE().getLogger().postError("Can't find main class for addon: " + file.getName() + " " + main);
                    continue;
                }

                if (cls.getSuperclass() == null) {
                    DBUtil.getINSTANCE().getLogger().postError("Main class does not extend any for addon: " + file.getName());
                    continue;
                }

                if (cls.getSuperclass() != Addon.class) {
                    DBUtil.getINSTANCE().getLogger().postError("Main class does not extend Addon.class for addon: " + file.getName());
                    continue;
                }

                if (cls.getConstructors().length != 1) {
                    DBUtil.getINSTANCE().getLogger().postError("Main class has not 1 constructor for addon: " + file.getName());
                    continue;
                }

                Object addonClass;

                AddonInfo info = new AddonInfo(name, version, author, main);

                if (addons.stream().anyMatch(addon -> addon.getAddonInfo().getName().equals(name))) {
                    DBUtil.getINSTANCE().getLogger().postMessage("Skipping addon " + name + " already running.");
                    continue;
                }

                File dataFolder = new File(DBUtil.getINSTANCE().getDataFolder().getAbsolutePath() + "/addons/" + name);

                try {
                    addonClass = cls.getConstructors()[0].newInstance(info, dataFolder, DBUtil.getINSTANCE().getLogger(), DBUtil.getINSTANCE().getCommandManager(), DBUtil.getINSTANCE().getMode());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    DBUtil.getINSTANCE().getLogger().postError("Cannot create addon class for unknown reason: " + file.getName());
                    e.printStackTrace();
                    continue;
                }


                DBUtil.getINSTANCE().getLogger().postMessage("Enabled addon " + name + " " + version);
                Addon addon = (Addon) addonClass;
                addon.onStart();
                addons.add(addon);
            }
        }
        return addons;
    }

    public static void stopAddons(List<Addon> addons) {
        addons.forEach(Addon::onEnd);
    }


}
