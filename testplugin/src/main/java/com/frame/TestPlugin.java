package com.frame;

import io.github.hedgehog1029.frame.Frame;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // Register MyModule in Frame
        Frame.addModule(MyModule.class);

    }
}
