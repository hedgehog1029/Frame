package io.github.hedgehog1029.frame.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {
	String[] aliases();
	String description();
	String permission() default "";
}
