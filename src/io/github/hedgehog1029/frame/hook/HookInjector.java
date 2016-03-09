package io.github.hedgehog1029.frame.hook;

import io.github.hedgehog1029.frame.annotations.Hook;
import io.github.hedgehog1029.frame.logger.Logger;
import io.github.hedgehog1029.frame.module.ModuleLoader;

import java.lang.reflect.Field;

public class HookInjector {
	public static void inject() {
		for (Class<?> c : ModuleLoader.getModuleClasses()) {
			Object instance = ModuleLoader.getInstance(c);

			for (Field f : c.getDeclaredFields()) {
				if (f.isAnnotationPresent(Hook.class)) {
					IPluginHook hook = HookLoader.get(f.getType());
					f.setAccessible(true);

					try {
						if (hook.available())
							f.set(instance, hook);
						else
							f.set(instance, null);
					} catch (IllegalAccessException e) {
						Logger.err("Couldn't access field " + f.getName() + "!");
					}
				}
			}
		}

		Logger.info("Injected hooks.");
	}
}
