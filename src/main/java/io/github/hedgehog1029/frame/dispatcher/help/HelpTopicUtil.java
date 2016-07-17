package io.github.hedgehog1029.frame.dispatcher.help;

import io.github.hedgehog1029.frame.loader.CommandMapping;
import io.github.hedgehog1029.frame.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.help.HelpTopic;

import java.util.ArrayList;
import java.util.HashMap;

public class HelpTopicUtil {
	private static HashMap<String, ArrayList<HelpTopic>> topics = new HashMap<>();

	public static void addHelpTopic(CommandMapping mapping) {
		if (!topics.containsKey(mapping.getHelpTopic()))
			topics.put(mapping.getHelpTopic(), new ArrayList<>());

		ManagedHelpTopic topic = new ManagedHelpTopic(mapping.getName(), mapping.getCommand(), mapping.getPermission());

		topics.get(mapping.getHelpTopic()).add(topic);
		Bukkit.getHelpMap().addTopic(topic);
	}

	public static void index() {
		topics.forEach((parent, topics) -> Bukkit.getHelpMap().addTopic(new IndexTopic(parent, topics)));

		Logger.info("HelpTopics registered.");
	}
}
