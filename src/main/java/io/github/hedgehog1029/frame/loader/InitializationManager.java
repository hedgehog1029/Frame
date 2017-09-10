package io.github.hedgehog1029.frame.loader;

import io.github.hedgehog1029.frame.annotations.PostInit;
import io.github.hedgehog1029.frame.annotations.PrimaryPostInit;
import io.github.hedgehog1029.frame.logger.Logger;
import io.github.hedgehog1029.frame.module.ModuleLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class InitializationManager {
	public static void primaryPost() {
		ModuleLoader.getModuleClasses().forEach(clazz -> {
			for (Method m : clazz.getDeclaredMethods()) {
				if (m.isAnnotationPresent(PrimaryPostInit.class)) {
					try {
						m.invoke(ModuleLoader.getInstance(clazz));
					} catch (InvocationTargetException | IllegalAccessException e) {
						Logger.warn("There was an error invoking a primary postInit on method " + m.getName());
						e.printStackTrace();
					}
				}
			}
		});
	}

	public static void postInit() {
		ModuleLoader.getModuleClasses().forEach(clazz -> {
			for (Method m : clazz.getDeclaredMethods()) {
				if (m.isAnnotationPresent(PostInit.class)) {
					try {
						m.invoke(ModuleLoader.getInstance(clazz));
					} catch (IllegalAccessException | InvocationTargetException e) {
						Logger.warn("There was an error invoking the postInit event on method " + m.getName());
						e.printStackTrace();
					}
				}
			}
		});
	}
}
