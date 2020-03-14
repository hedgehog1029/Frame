package io.github.hedgehog1029.frame.logging;

import java.util.logging.Level;

public class FrameLogger {
	private ILogReceiver receiver;

	public FrameLogger(ILogReceiver receiver) {
		this.receiver = receiver;
	}

	public void log(Level level, String message, Object... args) {
		String result = String.format(message, args);

		receiver.printLog(level, result);
	}

	public void severe(String message, Object... args) {
		this.log(Level.SEVERE, message, args);
	}

	public void warn(String message, Object... args) {
		this.log(Level.WARNING, message, args);
	}

	public void info(String message, Object... args) {
		this.log(Level.INFO, message, args);
	}

	public void fine(String message, Object... args) {
		this.log(Level.FINE, message, args);
	}
}
