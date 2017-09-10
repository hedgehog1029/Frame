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
import io.github.hedgehog1029.frame.loader.InitializationManager;
import io.github.hedgehog1029.frame.logger.Logger;
import io.github.hedgehog1029.frame.module.ModuleInjector;
import io.github.hedgehog1029.frame.module.ModuleLoader;

class Frame {
    static void main() {
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
	    InitializationManager.primaryPost();
	    InitializationManager.postInit();

        Logger.info("Finished Frame initialization.");
    }

    /*
     * Util functions for adding modules, configurations and hooks.
     */

	/**
	 * Register and load your module into Frame
	 * @param clazz Class containing your module
     */
    public static void addModule(Class clazz) {
        ModuleLoader.add(clazz);
    }

	/**
	 * Register a configuration class
	 * @param clazz Class annotated with {@link io.github.hedgehog1029.frame.annotations.Configuration}
	 */
	public static void addConfiguration(Class clazz) {
		ConfigurationBuilder.add(clazz);
	}

    /**
     * Register specified hook in Frame
     * @param clazz Class that implements {@link IPluginHook}
     */
    public static void addHook(Class<? extends IPluginHook> clazz) {
	    HookLoader.addHook(clazz);
    }

	public static Object getConfig(Class clazz) {
		return ConfigurationBuilder.get(clazz);
	}

	public static void reloadConfig(Class clazz) {
		ConfigurationBuilder.reload(clazz);
	}
}
