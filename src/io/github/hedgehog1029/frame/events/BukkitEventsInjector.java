package io.github.hedgehog1029.frame.events;

import io.github.hedgehog1029.frame.module.ModuleLoader;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitEventsInjector {
	public static void inject() {
		for (Class c : ModuleLoader.getModuleClasses()) {
			Object instance = ModuleLoader.getInstance(c);

			if (instance instanceof Listener)
				Bukkit.getPluginManager().registerEvents((Listener) instance, JavaPlugin.getProvidingPlugin(c));
		}
	}
}
