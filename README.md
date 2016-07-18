# Frame
A plugin framework for Spigot and Bukkit.

# Features
* Parametric Command Framework - easily register commands, without all the hassle!
* Automated command registering - automatically register those commands under Bukkit/Spigot!
* Automated /help registering - registers commands in Bukkit's /help.
* Dynamic configuration creation and injection - create configurations with classes, then inject them with updated values!
* Dynamic hook injection (with availability check) - create hooks for other plugins, and only provide them if the plugin is available.
* Automated listener registering - lets you use Bukkit event listeners in a module.

# Installation
1. [Grab the latest build of Frame](https://github.com/hedgehog1029/Frame/releases) and drop it into your plugins folder.
2. You're all set! All of your Frame-enabled plugins should now run perfectly.

# Developer Usage
[Grab the latest build of Frame](https://github.com/hedgehog1029/Frame/releases) and add it as a library dependency.

### Modules

The first thing you'll need to do is create a **module**. Modules are the core part of Frame; they can listen for Bukkit events and commands, use hooks and use configurations.
Modules don't need to implement an interface, so the following is valid:

```java
public class MyModule {
}
```

To register this module, you simply need to call `Frame.addModule()` from your plugin `onEnable()` method, like so:
```java
@Override
public void onEnable() {
 Frame.addModule(MyModule.class);
 // Your code
}
```

Frame handles all module instantiation in order to have a unified instance across the framework. If you wish to get hold of this instance, you can use `ModuleLoader.getInstance(<Module Class>)`.

#### Commands

It's time to register our first command! Add the following to your module class:

```java
public class MyModule {
  @Command(aliases = "hello", desc = "Hello, world!", usage = "/hello")
  @Permission("myplugin.hello")
  public void helloWorld(@Sender CommandSender sender) {
    sender.sendMessage("Hello, world!");
  }
}
```

Here we've introduced several new things:
* `@Command`: this annotation tells Frame to register the method as a command. It takes three parameters: 
  * `aliases`, which are the names for the command (e.g. an alias of `hello` results in the command "/hello" in Minecraft). It is a String[].
  * `desc`: this is used for the /help description.
  * `usage`: this is used for the /help usage.
* `@Permission`: this is an optional annotation, letting you specify a required permission node for this command.
* `@Sender`: this is our first parameter annotation (there are more!). This annotation tells the dispatcher that this parameter should be the sender object (the person who sent the command). Can be any subclass of CommandSender, but currently doesn't check the type (may be implemented in future).

We've now registered a command! If you built your plugin into a JAR and ran it on a server alongside Frame, running "/hello" would respond "Hello, world!".

In future I'll write more about commands here...

#### Hooks

Hooks are specialised objects designed to be used to hook into other plugins. To create a hook, you'll want a class that implements IPluginHook, like so:

```java
public class MyHook implements IPluginHook {
  @Override
  public boolean available() {
    return false;
  }
}
```

The `available()` method here should return whether the hook is available or not; if false, the hook will never be injected, and `null` will be passed instead.

You'll want to register your hook with Frame in your `onEnable()` like so:

```java
Frame.addHook(MyHook.class);
```

Here's an example hook from one of my plugins:

```java
public class WorldGuardHook implements IPluginHook {
	@Override
	public boolean available() {
		return Bukkit.getPluginManager().isPluginEnabled("WorldGuard");
	}

	public WorldGuardPlugin getWorldGuard() {
		return (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
	}
}
```

Once you've got your hook class, you can inject it into your module like so:

```java
public class MyModule {
  @Hook
  private MyHook hook;
  
  ...
}
```

This field will be populated if the hook is available, but null if not, so make sure to add a null check if you use it.

#### Bukkit Events

If you want to listen for Bukkit events in your module, you simply need to implement the Listener interface:
```java
public class MyModule implements Listener {
  ...
}
```

You can then use @EventHandler as normal.

#### Configurations

Coming soon(tm).

#### Other Useful Things

As well as using `ModuleLoader.getInstance()` to get module instances, you can also use the `@InjectModule` annotation:

```java
public class MyModule {
  @InjectModule
  public MyOtherModule other;
  
  ...
}
```
