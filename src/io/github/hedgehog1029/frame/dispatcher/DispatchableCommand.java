package io.github.hedgehog1029.frame.dispatcher;

import io.github.hedgehog1029.frame.dispatcher.exception.IncorrectArgumentsException;
import io.github.hedgehog1029.frame.dispatcher.exception.NoPermissionException;
import io.github.hedgehog1029.frame.dispatcher.help.ManagedHelpTopic;
import io.github.hedgehog1029.frame.inject.FrameInjector;
import io.github.hedgehog1029.frame.loader.CommandMapping;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicFactory;

import java.util.ArrayList;

public class DispatchableCommand extends Command {

    io.github.hedgehog1029.frame.loader.Command command;

    protected DispatchableCommand(String alias, CommandMapping mapping) {
        super(alias);

        this.command = mapping.getCommand();

        this.description = mapping.getCommand().desc();
        this.usageMessage = mapping.getCommand().usage();

        ManagedHelpTopic help = new ManagedHelpTopic(alias, command);

        if (mapping.getHelpTopic() != null) {
            if (!FrameInjector.helpTopics.containsKey(mapping.getHelpTopic()))
                FrameInjector.helpTopics.put(mapping.getHelpTopic(), new ArrayList<>());

            FrameInjector.helpTopics.get(mapping.getHelpTopic()).add(help);
        } else {
            if (!FrameInjector.helpTopics.containsKey("Commands"))
                FrameInjector.helpTopics.put("Commands", new ArrayList<>());

            FrameInjector.helpTopics.get("Commands").add(help);
        }

        Bukkit.getHelpMap().addTopic(help);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        CommandDispatcher dispatcher = FrameInjector.getDispatcher();

        try {
            return dispatcher.dispatch(commandSender, s, strings);
        } catch (IncorrectArgumentsException e) {
            commandSender.sendMessage(ChatColor.RED + e.getReason() + "\nUsage: " + dispatcher.getCommand(s).usage());
            return false;
        } catch (NoPermissionException e) {
            commandSender.sendMessage(ChatColor.RED + "You don't have permission!");
            return false;
        }
    }
}
