package de.staticred.dbv2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Custom annotation to make clear what kind of side an event is
 *
 * Its has no meaning for the code, just for more clearance among developers
 *
 * @author Devin
 * @version 1.0.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Side {

    public enum Proxy {
        /**
         * Bukkit side
         */
        BUKKIT,

        /**
         * Bungeecord side
         */
        BUNGEECORD,

        /**
         * no specific side
         */
        MIX
    }

    Proxy proxy() default Proxy.MIX;

}
