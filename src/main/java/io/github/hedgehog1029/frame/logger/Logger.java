package io.github.hedgehog1029.frame.logger;

import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Logger {
	public static void log(Level level, String msg) {
		Bukkit.getLogger().log(level, getPrefix() + msg);
	}

	public static void info(String msg) {
		log(Level.INFO, msg);
	}

	public static void warn(String msg) {
		log(Level.WARNING, msg);
	}

	public static void err(String msg) {
		log(Level.SEVERE, msg);
	}

	private static String getPrefix() {
		return "[Frame] ";
		// I want to try and get the plugin of the calling class, but that seems difficult + probably laggy
		// maybe instances...
	}
}
