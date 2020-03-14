package io.github.hedgehog1029.frame.inject;

import io.github.hedgehog1029.frame.logging.FrameLogger;
import io.github.hedgehog1029.frame.module.LoadedModule;
import io.github.hedgehog1029.frame.module.ModuleLoader;

import java.util.ArrayList;

public class FrameInjector {
	private ArrayList<Injector> injectors = new ArrayList<>();

	public FrameInjector injector(Injector injector) {
		injectors.add(injector);
		return this;
	}

    public void injectAll(ModuleLoader loader, FrameLogger logger) {
		for (LoadedModule<?> module : loader.getAllModules()) {
			for (Injector injector : injectors) {
				try {
					injector.inject(module);
				} catch (Exception e) {
					logger.severe("Injector '%s' threw an exception while traversing module %s",
							injector.getClass().getCanonicalName(), module);
					e.printStackTrace();
				}
			}
		}

		injectors.forEach(Injector::cleanup);
    }
}
