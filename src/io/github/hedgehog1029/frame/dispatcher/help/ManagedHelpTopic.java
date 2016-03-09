package io.github.hedgehog1029.frame.dispatcher.help;

import io.github.hedgehog1029.frame.annotations.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpTopic;

public class ManagedHelpTopic extends HelpTopic {

    public ManagedHelpTopic(String name, Command command) {
        this.name = "/" + name;
        this.shortText = command.desc();
        this.fullText = ChatColor.GOLD + "Description: " + ChatColor.RESET + command.desc() +
                ChatColor.GOLD + "\nUsage: " + ChatColor.RESET + command.usage();
    }

    @Override
    public boolean canSee(CommandSender commandSender) {
        return true;
    }
}
