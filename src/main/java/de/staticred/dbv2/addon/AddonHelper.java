package de.staticred.dbv2.addon;

import de.staticred.dbv2.DBUtil;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
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
public class AddonHelper {



    public static void loadAddonDirectory(File datafolder) {
        File addonDirectory = new File(datafolder.getAbsolutePath() + "/addons");
        addonDirectory.mkdirs();
    }


    /**
     * loads all addons in the given directory
     * @param directory directory containing all addons
     * @return list off all addons
     */
    public static List<Addon> loadAddons(File directory) {
        if (directory.isDirectory())
            throw new IllegalArgumentException("File must be directory");

        List<Addon> addons = new ArrayList<>();

        for (File file : directory.listFiles()) {

            if (file.getName().endsWith(".jar")) {
                //probably a .jar
                URL url = null;
                try {
                    url = file.toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    continue;
                }
                URL[] urls = new URL[]{url};

                // Create a new class loader with the directory
                ClassLoader cl = new URLClassLoader(urls);

                File[] contents = file.listFiles();

                File addonYML = null;

                for (File content : contents) {
                    if (content.getName().equals("addon.yml")) {
                        addonYML = content;
                        break;
                    }
                }

                if (addonYML == null) {
                    DBUtil.getINSTANCE().getLogger().postError("Can't load addon.yml for addon: " + file.getName());
                    continue;
                }

                YamlFile yamlConfig = new YamlFile(addonYML);

                String main = yamlConfig.getString("main");
                String name = yamlConfig.getString("name");

                Class cls;

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
                    DBUtil.getINSTANCE().getLogger().postError("Main class does not extend Addon class for addon: " + file.getName());
                    continue;
                }

                if (cls.getConstructors().length != 1) {
                    DBUtil.getINSTANCE().getLogger().postError("Main class has not 1 constructor for addon: " + file.getName());
                    continue;
                }

                Object addonClass;

                try {
                    addonClass = cls.getConstructors()[0].newInstance(name, DBUtil.getINSTANCE().getLogger(), DBUtil.getINSTANCE().getCommandManager(), yamlConfig, DBUtil.getINSTANCE().getMode());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    DBUtil.getINSTANCE().getLogger().postError("Cannot create addon class for unknown reason: " + file.getName());
                    e.printStackTrace();
                    continue;
                }
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
