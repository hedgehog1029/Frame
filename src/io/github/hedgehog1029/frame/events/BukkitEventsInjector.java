package io.github.hedgehog1029.frame.events;

import io.github.hedgehog1029.frame.inject.Injector;
import io.github.hedgehog1029.frame.module.ModuleLoader;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitEventsInjector implements Injector {
	@Override
	public void inject(Class<?> c, Object instance) {
		if (instance instanceof Listener)
			Bukkit.getPluginManager().registerEvents((Listener) instance, JavaPlugin.getProvidingPlugin(c));
	}
}
