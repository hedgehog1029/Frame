package io.github.hedgehog1029.frame.module;

import io.github.hedgehog1029.frame.annotations.InjectModule;
import io.github.hedgehog1029.frame.inject.Injector;
import io.github.hedgehog1029.frame.logger.Logger;

import java.lang.reflect.Field;

public class ModuleInjector implements Injector {
	@Override
	public void inject(Class<?> c, Object instance) {
		for (Field f : c.getDeclaredFields()) {
			if (f.isAnnotationPresent(InjectModule.class)) {
				if (!ModuleLoader.getModuleClasses().contains(f.getType())) {
					Logger.err("Couldn't find module of class " + f.getType().getName() + "!");
					continue;
				}

				f.setAccessible(true);
				try {
					f.set(instance, ModuleLoader.getInstance(f.getType()));
				} catch (IllegalAccessException e) {
					Logger.err("Couldn't access field " + f.getName() + "!");
				}
			}
		}
	}
}
