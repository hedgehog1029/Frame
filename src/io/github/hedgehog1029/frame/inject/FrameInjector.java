package io.github.hedgehog1029.frame.inject;

import io.github.hedgehog1029.frame.dispatcher.CommandDispatcher;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Singleton instances for Frame.
 */
public class FrameInjector {

    private static final CommandDispatcher DISPATCHER = new CommandDispatcher();

    public static CommandDispatcher getDispatcher() {
        return DISPATCHER;
    }

    public static CommandMap getCommandMap() {
        CommandMap map = null;

        try {
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);

            map = (CommandMap) field.get(Bukkit.getServer().getPluginManager());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static final HashMap<String, ArrayList<HelpTopic>> helpTopics = new HashMap<>();
}
