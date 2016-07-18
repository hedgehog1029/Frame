package io.github.hedgehog1029.frame;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Frame's Bukkit loader.
 */
public class FramePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTaskLater(this, Frame::main, 1);
    }
}
