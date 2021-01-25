package de.staticred.dbv2.commands.util;

import de.staticred.dbv2.player.DBUPlayer;

public interface DBUCommand {

    String getName();

    String getPermission();

    void execute(DBUPlayer player, String[] args);


}
