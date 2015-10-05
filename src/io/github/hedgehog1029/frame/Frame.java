package io.github.hedgehog1029.frame;

import io.github.hedgehog1029.frame.dispatcher.exception.CommandExistsException;
import io.github.hedgehog1029.frame.inject.FrameInjector;
import io.github.hedgehog1029.frame.loader.CommandLoader;
import io.github.hedgehog1029.frame.loader.exception.InaccessibleClassException;
import org.bukkit.Bukkit;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.IndexHelpTopic;

import java.util.ArrayList;
import java.util.Map;

public class Frame {

    public static void main() {
        Bukkit.getLogger().info("[Frame] Start Frame initialization.");

        CommandLoader loader = new CommandLoader();

        try {
            loader.load();
        } catch (CommandExistsException e) {
            Bukkit.getLogger().severe("CommandLoader failed to register a command because it already exists!");
        } catch (InaccessibleClassException e) {
            Bukkit.getLogger().severe("CommandLoader failed to instansiate class " + e.getOffender().getName() + "!");
        }

        for (Map.Entry<String, ArrayList<HelpTopic>> entry : FrameInjector.helpTopics.entrySet()) {
            Bukkit.getHelpMap().addTopic(new IndexHelpTopic(entry.getKey(), entry.getKey(), "", entry.getValue()));
        }

        Bukkit.getLogger().info("[Frame] Finished Frame initialization.");
    }

    /**
     * Util function for adding command-registering classes
     */

    public static void addCommandeer(Class clazz) {
        CommandLoader.add(clazz);
    }
}
