package com.frame;

import io.github.hedgehog1029.frame.annotations.Command;
import io.github.hedgehog1029.frame.annotations.Permission;
import io.github.hedgehog1029.frame.annotations.Sender;
import org.bukkit.command.CommandSender;

/**
 * Module class
 */
public class MyModule {
    @Command(aliases = "hello", desc = "Hello, World!", usage = "/hello")
    @Permission("myplugin.hello")
    public void helloWorld(@Sender CommandSender sender) {
        sender.sendMessage("Hello, World!");
    }
}
