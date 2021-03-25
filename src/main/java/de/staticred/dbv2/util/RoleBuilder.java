package de.staticred.dbv2.util;

import net.dv8tion.jda.api.entities.Role;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class RoleBuilder {

    /**
     * Builds role from a mention string
     *
     * <!id>
     *
     * @param mention the
     * @return
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
