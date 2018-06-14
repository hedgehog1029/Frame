package io.github.hedgehog1029.frame.util;

import java.lang.reflect.InvocationTargetException;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class ClassUtils {
	public static <T> T createInstance(Class<T> clazz) throws InstantiationException {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new InstantiationException("Could not create new target instance (private constructor?).");
		}
	}
}
