package io.github.hedgehog1029.frame.loader;

import io.github.hedgehog1029.frame.annotations.Command;
import io.github.hedgehog1029.frame.dispatcher.exception.CommandExistsException;
import io.github.hedgehog1029.frame.inject.FrameInjector;
import io.github.hedgehog1029.frame.loader.exception.InaccessibleClassException;
import io.github.hedgehog1029.frame.module.ModuleLoader;
import org.bukkit.Bukkit;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class CommandLoader {

    public void load() throws CommandExistsException, InaccessibleClassException {
        for (Class<?> c : ModuleLoader.getModuleClasses()) {
            Object instance = ModuleLoader.getInstance(c);

            final Method[] methods = c.getDeclaredMethods();

            for (Method method : methods) {
                if (method.isAnnotationPresent(Command.class)) {
                    Command details = method.getAnnotation(Command.class);

                    FrameInjector.getDispatcher().registerCommand(details, method, instance);
                }
            }
        }

        FrameInjector.getDispatcher().registerManagedCommandsWithBukkit();
    }
}
