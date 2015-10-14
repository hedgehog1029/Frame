package io.github.hedgehog1029.frame.dispatcher;

import io.github.hedgehog1029.frame.dispatcher.exception.IncorrectArgumentsException;
import io.github.hedgehog1029.frame.dispatcher.exception.NoPermissionException;
import io.github.hedgehog1029.frame.dispatcher.help.ManagedHelpTopic;
import io.github.hedgehog1029.frame.inject.FrameInjector;
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

    protected DispatchableCommand(String name, io.github.hedgehog1029.frame.loader.Command cmd) {
        super(name);

        this.command = cmd;

        this.description = cmd.desc();
        this.usageMessage = cmd.usage();

        ManagedHelpTopic help = new ManagedHelpTopic(name, cmd);

        if (!FrameInjector.helpTopics.containsKey(cmd.helpTopic()))
            FrameInjector.helpTopics.put(cmd.helpTopic(), new ArrayList<>());

        FrameInjector.helpTopics.get(cmd.helpTopic()).add(help);

        Bukkit.getHelpMap().addTopic(help);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        CommandDispatcher dispatcher = FrameInjector.getDispatcher();

        try {
            return dispatcher.dispatch(commandSender, s, strings);
        } catch (IncorrectArgumentsException e) {
            commandSender.sendMessage(ChatColor.RED + e.getReason() + " Usage: " + dispatcher.getCommand(s).usage());
            return false;
        } catch (NoPermissionException e) {
            commandSender.sendMessage(ChatColor.RED + "You don't have permission!");
            return false;
        }
    }
}
