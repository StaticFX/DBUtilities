package de.staticred.dbv2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Will be used over every event which can be canceled
 *
 * @author Devin
 * @version 1.0.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface Cancelable {
}
