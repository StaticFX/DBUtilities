package de.staticred.dbv2.events.util;

/**
 *
 * EventManager will manage you're custom events or you're bukkit/bungee events
 *
 * Its only used for the mc side and not for the discord side, for this use the BotHelper
 *
 * There are custom provided Events which can be helpfully because its combine bukkit/bungeecord events
 * If you're still missing some events, just add them directly for a bungeecord/bukkit event in here
 *
 *
 * @author Devin Fritz
 * @version 1.0.0
 */
public interface EventManager {

    /**
     * Registers an DBUtil listener
     * @param listener to register
     */
    void registerEvent(Listener listener);

    /**
     * Will fire the given event and all its methods
     * @param event to fire
     */
    void fireEvent(Event event);

    /**
     * inits the eventmanager
     */
    void init();
}
