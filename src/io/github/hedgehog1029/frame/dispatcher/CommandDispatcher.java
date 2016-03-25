package io.github.hedgehog1029.frame.dispatcher;

import io.github.hedgehog1029.frame.annotations.*;
import io.github.hedgehog1029.frame.annotations.Optional;
import io.github.hedgehog1029.frame.dispatcher.exception.CommandExistsException;
import io.github.hedgehog1029.frame.dispatcher.exception.IncorrectArgumentsException;
import io.github.hedgehog1029.frame.dispatcher.exception.NoPermissionException;
import io.github.hedgehog1029.frame.dispatcher.help.HelpTopicUtil;
import io.github.hedgehog1029.frame.inject.FrameInjector;
import io.github.hedgehog1029.frame.loader.CommandMapping;
import io.github.hedgehog1029.frame.loader.exception.InaccessibleMethodException;
import io.github.hedgehog1029.frame.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class CommandDispatcher {

	private static CommandDispatcher dispatcher;

	public static CommandDispatcher getDispatcher() {
		if (dispatcher == null) dispatcher = new CommandDispatcher();
		return dispatcher;
	}

    public void registerCommand(Command command, Method callback, Object instance) {
        CommandMapping mapping = new CommandMapping(command, callback, instance);

	    try {
		    getCommandMap().register(mapping.getName(), mapping);
		    HelpTopicUtil.addHelpTopic(mapping);
	    } catch (UnsupportedOperationException e) {
		    e.printStackTrace();
	    }
    }

    public final boolean dispatch(CommandSender sender, CommandMapping command, String... oargs) throws IncorrectArgumentsException, NoPermissionException {
        ArrayList<Object> params = new ArrayList<>();
        Parameter[] methodArgs = command.getMethod().getParameters();

	    ArrayDeque<Parameter> parameters = new ArrayDeque<>(Arrays.asList(methodArgs));
        ArrayDeque<String> args = new ArrayDeque<>(Arrays.asList(oargs));

	    while (!parameters.isEmpty()) {
		    Parameter current = parameters.pop();

		    if (args.peek() == null && isOptional(current)) {
			    params.add(null);
			    continue;
		    }

		    if (args.peek() == null && !isOptional(current) && !isSender(current))
			    throw new IncorrectArgumentsException(String.format("No argument provided for parameter %s!", current.getName()));

		    if (subclassOf(CommandSender.class, current)) {
			    if (isSender(current)) {
				    params.add(sender);
			    } else {
				    String spl = args.pop();
				    Player pl = Bukkit.getServer().getPlayer(spl);

				    if (pl != null) params.add(pl);
				    else throw new IncorrectArgumentsException(String.format("Couldn't find player %s! Are they online?", spl));
			    }
		    } else if (subclassOf(String.class, current)) {
			    if (current.isAnnotationPresent(Text.class)) {
				    StringBuilder builder = new StringBuilder();

				    args.forEach(a -> builder.append(a).append(" "));
				    params.add(builder.toString());

				    break; // this is a bad move but yeah
			    }
		    } else if (subclassOf(int.class, current)) {
			    int arg;

			    try {
				    arg = Integer.valueOf(args.pop());
			    } catch (NumberFormatException e) {
				    throw new IncorrectArgumentsException(String.format("Parameter %s requires an INTEGER.", current.getName()));
			    }

			    params.add(arg);
		    } else if (subclassOf(boolean.class, current)) {
			    String arg = args.pop();

			    if (arg.equalsIgnoreCase("true")) params.add(true);
			    else if (arg.equalsIgnoreCase("false")) params.add(false);
			    else throw new IncorrectArgumentsException(String.format("Parameter %s requires a BOOLEAN.", current.getName()));
		    } else if (subclassOf(OfflinePlayer.class, current)) {
			    String spl = args.pop();
			    OfflinePlayer opl = Bukkit.getServer().getOfflinePlayer(spl);

			    if (opl != null) params.add(opl);
			    else throw new IncorrectArgumentsException(String.format("Couldn't find offline player %s!", spl));
		    }
	    }

        // Check permission
        if (command.getPermission() != null && !sender.hasPermission(command.getPermission())) {
            throw new NoPermissionException();
        }

        // Invoke command
        try {
            command.invoke(params.toArray());
            return true;
        } catch (InaccessibleMethodException e) {
            sender.sendMessage(ChatColor.RED + "There was an error processing your command. Try again?");
            Logger.err("Could not invoke method for command " + command.getName() + "!");
            e.printStackTrace();
            return false;
        }
    }

	// Utilities

	private static boolean subclassOf(Class<?> c, Parameter p) {
		return c.isAssignableFrom(p.getType());
	}

	private static boolean isOptional(Parameter p) {
		return p.isAnnotationPresent(Optional.class);
	}

	private static boolean isSender(Parameter p) {
		return p.isAnnotationPresent(Sender.class) && subclassOf(CommandSender.class, p);
	}

	private static CommandMap map;
	private static CommandMap getCommandMap() {
		if (map == null) {
			try {
				Field field = SimplePluginManager.class.getDeclaredField("commandMap");
				field.setAccessible(true);

				map = (CommandMap) field.get(Bukkit.getServer().getPluginManager());
			} catch (NoSuchFieldException | IllegalAccessException e) {
				Logger.err("Problem getting the command map!");
			}
		}

		return map;
	}
}
