package de.staticred.dbv2.events.util;

import de.staticred.dbv2.annotations.Side;

/**
 * Describes and custom event
 *
 * @author Devin
 * @version 1.0.0
 */
public abstract class Event {

    public abstract Side.Proxy getSide();

    public abstract Class<?> getEventSubClass();

}
