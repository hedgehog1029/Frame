package io.github.hedgehog1029.frame.dispatcher.help;

import org.bukkit.help.HelpTopic;
import org.bukkit.help.IndexHelpTopic;

import java.util.List;

public class IndexTopic extends IndexHelpTopic {
	public IndexTopic(String name, List<HelpTopic> topics) {
		super(name, "Help for " + name, "", topics);
	}
}
