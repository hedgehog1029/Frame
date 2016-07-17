package io.github.hedgehog1029.frame;

import io.github.hedgehog1029.frame.config.ConfigurationBuilder;
import io.github.hedgehog1029.frame.config.ConfigurationInjector;
import io.github.hedgehog1029.frame.dispatcher.help.HelpTopicUtil;
import io.github.hedgehog1029.frame.events.BukkitEventsInjector;
import io.github.hedgehog1029.frame.hook.HookInjector;
import io.github.hedgehog1029.frame.hook.HookLoader;
import io.github.hedgehog1029.frame.hook.IPluginHook;
import io.github.hedgehog1029.frame.inject.FrameInjector;
import io.github.hedgehog1029.frame.loader.CommandInjector;
import io.github.hedgehog1029.frame.logger.Logger;
import io.github.hedgehog1029.frame.module.ModuleInjector;
import io.github.hedgehog1029.frame.module.ModuleLoader;

public class Frame {

    public static void main() {
        Logger.info("Start Frame initialization.");

	    // Build configurations
	    try {
		    ConfigurationBuilder.buildAwaiting();
	    } catch (Exception e) {
		    Logger.err("Error building configurations!");
		    Logger.err(e.getMessage());

		    e.printStackTrace();
	    }

	    new FrameInjector()
			    .injector(new CommandInjector())
			    .injector(new HookInjector())
			    .injector(new ModuleInjector())
			    .injector(new ConfigurationInjector())
			    .injector(new BukkitEventsInjector())
			    .injectAll();

	    HelpTopicUtil.index();

        Logger.info("Finished Frame initialization.");
    }

    /**
     * Util functions for adding modules, configurations and hooks.
     */

    public static void addModule(Class clazz) {
        ModuleLoader.add(clazz);
    }

	public static void addConfiguration(Class clazz) {
		ConfigurationBuilder.add(clazz);
	}

    public static void addHook(Class<? extends IPluginHook> clazz) {
	    HookLoader.addHook(clazz);
    }

	public static Object getConfig(Class clazz) {
		return ConfigurationBuilder.get(clazz);
	}
}
