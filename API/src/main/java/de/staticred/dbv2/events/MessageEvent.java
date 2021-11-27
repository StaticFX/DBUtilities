package de.staticred.dbv2.events;

import de.staticred.dbv2.annotations.Cancelable;
import de.staticred.dbv2.annotations.Side;
import de.staticred.dbv2.events.util.Event;
import de.staticred.dbv2.player.DBUPlayer;

/**
 * Fired when a user sends something in the text
 *
 * @author Devin
 * @version 1.0.0
 */
public class MessageEvent extends Event {

    /**
     * Player who send the Message
     */
    private DBUPlayer player;

    private Side.Proxy side;

    /**
     * Message
    */
    private String message;

    /**
     * If the event was canceled or not
     */
    private boolean canceled;

    @Cancelable
    public MessageEvent(DBUPlayer player, String message, boolean canceled, Side.Proxy side) {
        this.player = player;
        this.message = message;
        this.canceled = canceled;
        this.side = side;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public DBUPlayer getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Class<?> getEventSubClass() {
        return this.getClass();
    }

    @Override
    public Side.Proxy getSide() {
        return side;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
