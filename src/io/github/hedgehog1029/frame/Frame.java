package io.github.hedgehog1029.frame;

import io.github.hedgehog1029.frame.config.ConfigurationBuilder;
import io.github.hedgehog1029.frame.dispatcher.exception.CommandExistsException;
import io.github.hedgehog1029.frame.hook.HookLoader;
import io.github.hedgehog1029.frame.hook.IPluginHook;
import io.github.hedgehog1029.frame.inject.FrameInjector;
import io.github.hedgehog1029.frame.loader.CommandLoader;
import io.github.hedgehog1029.frame.loader.exception.InaccessibleClassException;
import io.github.hedgehog1029.frame.logger.Logger;
import io.github.hedgehog1029.frame.module.ModuleLoader;
import org.bukkit.Bukkit;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.IndexHelpTopic;

import java.util.ArrayList;
import java.util.Map;

public class Frame {

    public static void main() {
        Logger.info("Start Frame initialization.");

        CommandLoader loader = new CommandLoader();

        try {
            loader.load();
        } catch (CommandExistsException e) {
            Logger.err("CommandLoader failed to register a command because it already exists!");
        } catch (InaccessibleClassException e) {
            Logger.err("CommandLoader failed to instansiate class " + e.getOffender().getName() + "!");
        }

        for (Map.Entry<String, ArrayList<HelpTopic>> entry : FrameInjector.helpTopics.entrySet()) {
            Bukkit.getHelpMap().addTopic(new IndexHelpTopic(entry.getKey(), entry.getKey(), "", entry.getValue()));
        }

        Logger.info("Finished Frame initialization.");
    }

    /**
     * Util function for adding command-registering classes
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
}
