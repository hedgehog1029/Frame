package io.github.hedgehog1029.frame.loader;

import io.github.hedgehog1029.frame.loader.exception.InaccessibleMethodException;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CommandMapping {
    private Command command;
    private Method method;
    private Object container;

    public CommandMapping(Command cmd, Method method, Object object) {
        this.command = cmd;
        this.method = method;
        this.container = object;
    }

    public Command getCommand() {
        return this.command;
    }

    public Method getMethod() {
        return this.method;
    }

    public String[] getAliases() {
        return this.command.aliases();
    }

    public void invoke(Object... args) throws InaccessibleMethodException {
        try {
            method.invoke(container, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new InaccessibleMethodException(method);
        }
    }
}
