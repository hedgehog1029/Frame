package io.github.hedgehog1029.frame.inject;

import io.github.hedgehog1029.frame.module.LoadedModule;
import io.github.hedgehog1029.frame.module.ModuleLoader;

import java.util.ArrayList;

public class FrameInjector {
	private ArrayList<Injector> injectors = new ArrayList<>();

	public FrameInjector injector(Injector injector) {
		injectors.add(injector);
		return this;
	}

    public void injectAll(ModuleLoader loader) {
		for (LoadedModule module : loader.getAllModules()) {
			for (Injector injector : injectors) {
				try {
					injector.inject(module);
				} catch (Exception e) {
					System.out.println(String.format("Injector %s threw an exception while traversing module %s",
							injector.getClass().getSimpleName(), module));
					e.printStackTrace();
				}
			}
		}
    }
}
