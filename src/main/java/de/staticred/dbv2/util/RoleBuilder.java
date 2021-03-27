package de.staticred.dbv2.util;

import net.dv8tion.jda.api.entities.Role;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class RoleBuilder {

    /**
     * Builds role from string whether its a mention or just the id
     * @param string the string
     * @return role if found
     */
    public static Role buildRoleFromMessage(String string) {
        if (string.startsWith("<"))
            return getRoleByMention(string);

        long roleID;
        try {
            roleID = Long.parseLong(string);
        } catch (NumberFormatException e) {
            return null;
        }

        return BotHelper.jda.getRoleById(roleID);
    }

    /**
     * Builds role from a mention string
     *
     * <!id>
     *
     * @param mention the
     * @return role if found
     */
    public static Role getRoleByMention(String mention) {

        String roleString = mention.substring(3, mention.length() - 1);
        long roleID;
        try {
            roleID = Long.parseLong(roleString);
        } catch (NumberFormatException e) {
            return null;
        }

        return BotHelper.jda.getRoleById(roleID);
    }

}
