package io.github.hedgehog1029.frame.dispatcher;

import io.github.hedgehog1029.frame.dispatcher.exception.CommandExistsException;
import io.github.hedgehog1029.frame.dispatcher.exception.IncorrectArgumentsException;
import io.github.hedgehog1029.frame.dispatcher.exception.NoPermissionException;
import io.github.hedgehog1029.frame.inject.FrameInjector;
import io.github.hedgehog1029.frame.loader.Command;
import io.github.hedgehog1029.frame.loader.CommandMapping;
import io.github.hedgehog1029.frame.loader.Sender;
import io.github.hedgehog1029.frame.loader.Text;
import io.github.hedgehog1029.frame.loader.exception.InaccessibleMethodException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandDispatcher {

    private final Map<String, CommandMapping> commands = new HashMap<>();

    public void registerCommand(Command command, Method callback, Object instance) throws CommandExistsException {
        CommandMapping mapping = new CommandMapping(command, callback, instance);

        for (String a : command.aliases()) {
            if (commands.containsKey(a))
                throw new CommandExistsException(a);

            commands.put(a, mapping);
        }
    }

    public void registerManagedCommandsWithBukkit() {
        CommandMap map = FrameInjector.getCommandMap();
        for (Map.Entry<String, CommandMapping> entry : commands.entrySet()) {
            map.register(entry.getKey(), new DispatchableCommand(entry.getKey(), entry.getValue().getCommand()));
        }

        Bukkit.getLogger().info("[Frame] Registered " + commands.size() + " commands.");
    }

    public final boolean dispatch(CommandSender sender, String cmd, String... oargs) throws IncorrectArgumentsException, NoPermissionException {
        if (!commands.containsKey(cmd))
            return true;

        CommandMapping command = commands.get(cmd);

        ArrayList<Object> params = new ArrayList<>();
        Parameter[] methodArgs = command.getMethod().getParameters();
        ArrayList<String> args = new ArrayList<>(Arrays.asList(oargs));

        for (int i = 0; i < methodArgs.length; i++) {
            Parameter p = methodArgs[i];
            String s;

            try {
                if (args.size() == 0) {
                    s = "NULL";
                } else {
                    s = args.get(i);
                }
            } catch (IndexOutOfBoundsException e) {
                throw new IncorrectArgumentsException("Wrong number of arguments!");
            }


            if (String.class.isAssignableFrom(p.getType())) {
                if (p.isAnnotationPresent(Text.class)) {
                    String text = "";

                    for (int k = 0; k < args.size(); k++) {
                        if (k < i - 1)
                            continue;

                        text += args.get(k) + " ";
                    }

                    params.add(text);

                    break;
                } else {
                    params.add(s);
                }
            } else if (int.class.isAssignableFrom(p.getType())) {
                int j;
                try { j = Integer.valueOf(s); } catch (NumberFormatException e) { throw new IncorrectArgumentsException("Argument " + i + "is not an integer!"); }

                params.add(j);
            } else if (boolean.class.isAssignableFrom(p.getType())) {
                if (s.equalsIgnoreCase("true")) params.add(true);
                else if (s.equalsIgnoreCase("false")) params.add(false);
                else throw new IncorrectArgumentsException("Argument " + i + " is not a boolean!");
            } else if (CommandSender.class.isAssignableFrom(p.getType())) {
                if (p.isAnnotationPresent(Sender.class)) {
                    args.add(i, "SENDERNULL");

                    params.add(sender);
                } else {
                    Player pl = Bukkit.getServer().getPlayer(s);

                    if (pl != null) params.add(pl);
                    else throw new IncorrectArgumentsException("Could not find player " + s + "!");
                }
            } else if (OfflinePlayer.class.isAssignableFrom(p.getType())) {
                OfflinePlayer pl = Bukkit.getServer().getOfflinePlayer(s);

                if (pl != null) params.add(pl);
                else throw new IncorrectArgumentsException("Could not find offline player " + s + "!");
            } else throw new IncorrectArgumentsException("The underlying function contains an unsupported parameter type (This is a PLUGIN issue, not a USER issue).");
        }

        // Check all arguments are fufilled
        if (params.size() != methodArgs.length) {
            throw new IncorrectArgumentsException("Not enough arguments!");
        }

        // Check permission
        if (!command.getCommand().permission().equals("") && !sender.hasPermission(command.getCommand().permission())) {
            throw new NoPermissionException();
        }

        // Invoke command
        try {
            command.invoke(params.toArray());
            return true;
        } catch (InaccessibleMethodException e) {
            sender.sendMessage(ChatColor.RED + "There was an error processing your command. Try again?");
            Bukkit.getLogger().severe("Could not invoke method for command " + cmd + "!");
            e.printStackTrace();
            return false;
        }
    }

    // Functions used in DispatchableCommand to get information about commands

    public Command getCommand(String name) {
        return commands.get(name).getCommand();
    }
}
