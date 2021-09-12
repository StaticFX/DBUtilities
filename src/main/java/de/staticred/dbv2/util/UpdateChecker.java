package de.staticred.dbv2.util;

import de.staticred.dbv2.DBUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class UpdateChecker {

    private final Logger logger;
    private final int id;
    private boolean latest;

    public UpdateChecker(Logger logger, int id) {
        this.logger = logger;
        this.id = id;

        checkUpdate((version) -> {
            if (!DBUtil.VERSION.equals(version)) {
                latest = false;
                logger.postMessage("You are not on the latest version, download it over here: https://www.spigotmc.org/resources/dbutil-strong-discord-mc-api.90724/");
            } else {
                latest = true;
            }
        });
    }

    public boolean isLatest() {
        return latest;
    }

    public void checkUpdate(Consumer<String> consumer) {
        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.id).openStream(); Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNext()) {
                consumer.accept(scanner.next());
            }
        } catch (IOException exception) {
            logger.postError("Cannot look for updates: " + exception.getMessage());
        }
    }

}
