package io.github.hedgehog1029.frame.logging;

import java.util.logging.Level;

public class SystemLogReceiver implements ILogReceiver {
	@Override
	public void printLog(Level logLevel, String message) {
		String out = String.format("[Frame] %s %s", logLevel, message);

		System.err.println(out);
	}
}
