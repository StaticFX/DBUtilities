package de.staticred.dbv2.events;

import de.staticred.dbv2.annotations.Side;
import de.staticred.dbv2.events.util.Event;
import de.staticred.dbv2.player.DBUPlayer;

/**
 * Fired whenever someone joined the server
 *
 * @author Devin
 * @version 1.0.0
 */
public class JoinEvent extends Event {

    /**
     * Player who joined
     */
    private DBUPlayer player;

    private Side.Proxy side;

    /**
     * Constructor.
     * @param player joiner
     */
    public JoinEvent(DBUPlayer player, Side.Proxy side) {
        this.player = player;
        this.side = side;
    }

    public DBUPlayer getPlayer() {
        return player;
    }

    @Override
    public Class<?> getEventSubClass() {
        return this.getClass();
    }

    @Override
    public Side.Proxy getSide() {
        return side;
    }
}
