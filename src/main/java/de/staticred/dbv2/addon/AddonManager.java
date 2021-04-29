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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Util class helping loading all the addons and managing them
 *
 * @author Devin
 * @version 1.0.0
 */
public class AddonManager {

    private final DBUtil session;
    private final Set<Addon> addons;
    private final File addonDirectory;

    public AddonManager(DBUtil session, File dataFolder) {
        addons = new HashSet<>();
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
    public Set<Addon> loadAddons() {
        if (!addonDirectory.isDirectory())
            throw new IllegalArgumentException("File must be directory");

        for (File file : addonDirectory.listFiles()) {
            if (file.getName().endsWith(".jar")) {
                loadAddon(file);
            }
        }
        return addons;
    }

    private void loadAddon(File jarFile) {
        ClassLoader cl = getClassLoader(jarFile);

        if (cl == null) {
            DBUtil.getINSTANCE().getLogger().postError("Cant load classes for Addon " + jarFile.getName());
            return;
        }

        // Create a new class loader with the directory

        YamlFile yamlConfig = getYamlFileFor(cl, jarFile);

        if (yamlConfig == null) {
            DBUtil.getINSTANCE().getLogger().postError("Cant load YAMLFile for Addon " + jarFile.getName());
            return;
        }

        String main = yamlConfig.getString("main");
        String name = yamlConfig.getString("name");
        String author = yamlConfig.getString("author");
        String version = yamlConfig.getString("version");
        List<String> dependencies = yamlConfig.getStringList("depends");

        if (addons.stream().anyMatch(addon -> addon.getAddonInfo().getName().equalsIgnoreCase(name))) {
            DBUtil.getINSTANCE().getLogger().postError("Cant load addon if already load " + jarFile.getName());
            return;
        }

        if (dependencies.contains(name)) {
            DBUtil.getINSTANCE().getLogger().postError("Addon cant depend on itself " + jarFile.getName());
            return;
        }

        for (String dependency : dependencies) {
            if (addons.stream().anyMatch(addon -> addon.getAddonInfo().getName().equalsIgnoreCase(dependency)))
                continue;
            if (!loadDependency(dependency)) {
                DBUtil.getINSTANCE().getLogger().postError("Cant load dependency " + dependency + " for addon " + jarFile.getName());
                return;
            }
        }

        if (version == null)
            version = "N/A";

        if (main == null) {
            DBUtil.getINSTANCE().getLogger().postError("Can't find main class for addon: " + jarFile.getName());
            return;
        }

        if (name == null) {
            DBUtil.getINSTANCE().getLogger().postError("Can't find name for addon: " + jarFile.getName());
            return;
        }

        Class<?> cls;

        try {
            cls = cl.loadClass(main);
        } catch (ClassNotFoundException e) {
            DBUtil.getINSTANCE().getLogger().postError("Can't find main class for addon: " + jarFile.getName() + " " + main);
            return;
        }

        if (cls.getSuperclass() == null) {
            DBUtil.getINSTANCE().getLogger().postError("Main class does not extend any for addon: " + jarFile.getName());
            return;
        }

        if (cls.getSuperclass() != Addon.class) {
            DBUtil.getINSTANCE().getLogger().postError("Main class does not extend Addon.class for addon: " + jarFile.getName());
            return;
        }

        if (cls.getConstructors().length != 1) {
            DBUtil.getINSTANCE().getLogger().postError("Main class has not 1 constructor for addon: " + jarFile.getName());
            return;
        }

        Object addonClass;

        AddonInfo info = new AddonInfo(name, version, author, main);

        if (addons.stream().anyMatch(addon -> addon.getAddonInfo().getName().equals(name))) {
            DBUtil.getINSTANCE().getLogger().postMessage("Skipping addon " + name + " already running.");
            return;
        }

        File dataFolder = new File(DBUtil.getINSTANCE().getDataFolder().getAbsolutePath() + "/addons/" + name);

        try {
            addonClass = cls.getConstructors()[0].newInstance(info, dataFolder, DBUtil.getINSTANCE().getLogger(), DBUtil.getINSTANCE().getCommandManager(), DBUtil.getINSTANCE().getMode());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            DBUtil.getINSTANCE().getLogger().postError("Cannot create addon class for unknown reason: " + jarFile.getName());
            e.printStackTrace();
            return;
        }

        DBUtil.getINSTANCE().getLogger().postMessage("Enabled addon " + name + " " + version);
        Addon addon = (Addon) addonClass;
        addon.onStart();
        addons.add(addon);
    }

    private boolean loadDependency(String name) {
        for (File file : addonDirectory.listFiles()) {
            if (file.isDirectory())
                continue;

            if (!file.getName().endsWith(".jar"))
                continue;

            ClassLoader cl = getClassLoader(file);

            if (cl == null) {
                DBUtil.getINSTANCE().getLogger().postError("Cant load classes for Addon " + file.getName());
                continue;
            }

            YamlFile yamlFile = getYamlFileFor(cl, file);


            if (yamlFile == null) {
                DBUtil.getINSTANCE().getLogger().postError("Cant load YAMLFile for Addon " + file.getName());
                continue;
            }

            if (yamlFile.getString("name").equalsIgnoreCase(name)) {
                loadAddon(file);
                return true;
            }
        }

        return false;
    }

    private ClassLoader getClassLoader(File jar) {
        URL url;
        try {
            url = jar.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        URL[] urls = new URL[]{url};

        return new URLClassLoader(urls, this.getClass().getClassLoader());
    }

    private YamlFile getYamlFileFor(ClassLoader cl, File jarFile) {

        InputStream stream = cl.getResourceAsStream("addon.yml");

        if (stream == null) {
            DBUtil.getINSTANCE().getLogger().postError("Addon.yml was not found for addon: " + jarFile.getName());
            return null;
        }

        YamlFile yamlConfig;
        try {
            yamlConfig = new YamlFile();
            yamlConfig.load(stream);
        } catch (InvalidConfigurationException | IOException e) {
            DBUtil.getINSTANCE().getLogger().postError("Can't load addon.yml for addon: " + jarFile.getName());
            e.printStackTrace();
            return null;
        }
        return yamlConfig;
    }

    public void stopAddons() {
        addons.forEach(Addon::onEnd);
    }


}
