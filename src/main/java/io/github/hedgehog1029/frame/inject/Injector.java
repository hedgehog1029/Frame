package io.github.hedgehog1029.frame.inject;

import io.github.hedgehog1029.frame.module.LoadedModule;

public interface Injector {
	void inject(LoadedModule<?> module);
}
