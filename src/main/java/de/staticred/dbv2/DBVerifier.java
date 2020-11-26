package de.staticred.dbv2;

import de.staticred.dbv2.util.Mode;

/**
 * DBVerifier 2.0
 *
 * DBVerifier is a minecraft based on bukkit or bungeecord. It stores data about mc accounts and discord accounts linked together.
 * Plugin will work on Bungeecord aswell on bukkit, bases ond BungeeCord 1.16 and Bukkit 1.16
 * Using Database aswell as a file system, Database HikariCP
 * FileSystem will update automatically
 *
 * @author StaticRed
 * @version 2.0.0
 */

public class DBVerifier {


    /*todo - Add independent command execution (neverthenless bukkit or bungeecord)
           - Add independent events (neverthenless bukkit or bungeecord)
           - Add FileSystem (config.yml, messagefiles think about new system)
           - Add FileSystem updater
           - Add DataBase (with updater from VESE)
           - Add Methods to download database data and store them and also restore them
           - Add AutoBackUp
           - Add LinkingFeatures (MC -> DC, DC -> MC)
           - Add all Old command from DBV 1.0
           - Readd metric system (with new stats aswell)
           - Add updater
           - Add Debuger with javaDocs

     */

    /** In which mode the plugin runs (BUKKIT,BUNGEECORD)*/
    public static Mode mode;

    /**
     * Main Constructor of DBVerifier 2.0
     * Call this method to start up the plugin.
     * @param mode whether Bukkit or Bungeecord
     */
    public DBVerifier(boolean mode) {
    }



}
