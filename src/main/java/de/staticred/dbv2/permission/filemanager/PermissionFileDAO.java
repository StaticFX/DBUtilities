package de.staticred.dbv2.permission.filemanager;

import de.staticred.dbv2.files.util.DBUtilFile;
import de.staticred.dbv2.permission.PermissionDAO;
import de.staticred.dbv2.util.BotHelper;
import net.dv8tion.jda.api.entities.Role;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionFileDAO extends DBUtilFile implements PermissionDAO {

    public static final String NAME = "permissions.yml";

    /**
     * whether the file is valid or not
     */
    private boolean isValidFile;

    /**
     * Constructor.
     * @param file location
     */
    public PermissionFileDAO(File file) {
        super(file);
    }

    @Override
    public void afterLoad() {
    }

    @Override
    public boolean hasPermission(long id, String permission) {
        Map<String, Boolean> permissionMap = getPermissions(id, false);

        if (!permissionMap.containsKey(permission))
            return false;

        return permissionMap.get(permission);
    }

    @Override
    public boolean startDAO() throws IOException {
        return false;
    }

    @Override
    public void setPermission(long id, String permission, boolean enabled) {
        String role = Long.toString(id);

        if (!configuration.contains(role)) {
            addRole(id);
        }

        List<String> permissions = configuration.getStringList(role + ".permission");

        permissions.add(permission);

        configuration.set(role + ".permission", permissions);

        if (enabled) {
            List<String> enableds = configuration.getStringList(role + ".enabled");
            enableds.add(permission);
            configuration.set(role + ".enabled", permissions);
        }

        saveData();
    }

    public void addRole(long role) {
        configuration.set(role + ".permission", new ArrayList<String>());
        configuration.set(role + ".inherit", new ArrayList<String>());
        configuration.set(role + ".enabled", new ArrayList<String>());
        saveData();
    }

    @Override
    public List<Role> getInheritingRoles(long role) {
        List<Role> returnList = new ArrayList<>();

        List<String> inheriting = configuration.getStringList(role + ".inherit");

        for (String inherit : inheriting) {
            Role dcRole = BotHelper.jda.getRoleById(inherit);


            if (dcRole == null) {
                removeRole(Long.parseLong(inherit));
                continue;
            }
            returnList.add(dcRole);
        }

        return returnList;
    }

    public void removeRole(long role) {
        configuration.set(Long.toString(role), null);
        saveData();
    }

    @Override
    public Map<String, Boolean> getPermissions(long role, boolean deep) {
        HashMap<String, Boolean> permissionMap = new HashMap<>();

        List<String> permissions = configuration.getStringList(role + ".permission");
        List<String> enabled = configuration.getStringList(role + ".enabled");

        for (String permission : permissions) {
            permissionMap.put(permission, enabled.contains(permission));
            if (deep) {
                List<String> inheriting = configuration.getStringList(role + ".inherit");
                for (String inherit : inheriting) {
                    Map<String, Boolean> permissionInherit = getPermissions(Long.parseLong(inherit), true);
                    permissionMap.putAll(permissionInherit);
                }
            }
        }

        return permissionMap;
    }

    @Override
    public void removePermission(long role, String permission) {
        List<String> permissions = configuration.getStringList(role + ".permission");
        List<String> enabled = configuration.getStringList(role + ".enabled");


        permissions.remove(permission);
        enabled.remove(permission);

        configuration.set(role + ".permission", permissions);
        configuration.set(role + ".enabled", enabled);
        saveData();
    }

    @Override
    public void addInherit(long role, long inherit) {
        List<String> inheriting = configuration.getStringList(role + ".inherit");
        inheriting.add(Long.toString(inherit));
        configuration.set(role + ".inherit", inheriting);
        saveData();
    }

    @Override
    public void removeInherit(long role, long inherit) {
        List<String> inheriting = configuration.getStringList(role + ".inherit");
        inheriting.remove(Long.toString(inherit));
        configuration.set(role + ".inherit", inheriting);
        saveData();
    }


    @Override
    public void setEnabledState(long id, String permission, boolean state) {
        List<String> enabled = configuration.getStringList(id + ".enabled");
        List<String> permissions = configuration.getStringList(id + ".permission");

        if (!permissions.contains(permission))
            permissions.add(permission);

        if (state) {
            if (!enabled.contains(permission))
                enabled.add(permission);
        } else {
            enabled.remove(permission);
        }

        configuration.set(id + ".permission", permissions);
        configuration.set(id + ".enabled", enabled);
        saveData();
    }

    @Override
    public YamlFile asYaml() {
        return configuration;
    }
}
