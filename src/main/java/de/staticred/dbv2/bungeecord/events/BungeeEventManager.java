package de.staticred.dbv2.bungeecord.events;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.annotations.EventHandler;
import de.staticred.dbv2.bungeecord.DBVerifierPlugin;
import de.staticred.dbv2.events.util.Event;
import de.staticred.dbv2.events.util.EventManager;
import net.md_5.bungee.api.plugin.Listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class BungeeEventManager implements EventManager {

    public Set<Listener> events;
    public Set<de.staticred.dbv2.events.util.Listener> dbuEvents;


    public BungeeEventManager() {
        this.events = new HashSet<>();
        this.dbuEvents = new HashSet<>();
    }

    /**
     * registers an bungee event
     * @param listener event
     */
    public void registerEvent(Listener listener) {
        events.add(listener);
    }

    /**
     * registers an dbu event
     * @param listener event
     */
    public void registerEvent(de.staticred.dbv2.events.util.Listener listener) {
        dbuEvents.add(listener);
    }

    /**
     * inits the registered events, useless to call, dbu will call it after all addons have been loaded
     */
    public void init() {
        for (Listener listener : events) {
            DBVerifierPlugin.getInstance().getProxy().getPluginManager().registerListener(DBVerifierPlugin.getInstance(), listener);
        }
    }

    @Override
    public void fireEvent(Event event) {
        for(de.staticred.dbv2.events.util.Listener listener : dbuEvents) {
            for(final Method method : listener.getClass().getMethods()) {
                if(method.isAnnotationPresent(EventHandler.class)) {
                    if(method.getParameters().length != 1) {
                        DBUtil.getINSTANCE().getLogger().postError("Illegal Event structure. Only use 1 argument per Event Method.");
                        return;
                    }
                    for(final Parameter parameter : method.getParameters()) {
                        if(Event.class.isAssignableFrom(parameter.getType())) {
                            if(parameter.getType().getName().equalsIgnoreCase(event.getClass().getName())) {
                                try {
                                    method.invoke(listener,event);
                                    DBUtil.getINSTANCE().getLogger().postError("Method " + method.getName() + " fired!");
                                } catch (IllegalAccessException e) {
                                    DBUtil.getINSTANCE().getLogger().postError("Method is inaccessible, please check the following lines.");
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    DBUtil.getINSTANCE().getLogger().postError("Invalid method structure, please check the following lines.");
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
