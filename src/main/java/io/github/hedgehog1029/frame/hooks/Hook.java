package io.github.hedgehog1029.frame.hooks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A hook annotation may be placed on a field, and given a plugin name and class to fill in on success.
 * If the mod is loaded, the field is populated with a new instance of the class returned by #target().
 * If the mod is not loaded, the field is populated with a new instance of the field type.
 *
 * It is suggested for the base hook type to be a "dummy" type, with sentinel or empty methods.
 * The target class can then override this dummy class, implementing the actual behavior required.
 *
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Hook {
	String value();
	Class<?> target();
}
