package io.github.hedgehog1029.frame.logging;

import java.util.logging.Level;

public interface ILogReceiver {
	void printLog(Level logLevel, String message);
}
