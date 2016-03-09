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
1. [Grab the latest build of Frame]() and drop it into your plugins folder.
2. You're all set! All of your Frame-enabled plugins should now run perfectly.

# Developer Usage
[Grab the latest build of Frame]() and add it as a library dependency.

The first thing you'll need to do is create a **module**. Modules are the core part of Frame; they can listen for Bukkit events and commands, use hooks and use configurations.
Modules don't need to implement an interface, so the following is valid:

```java
public class MyModule {
}
```

To register this module, you simply need to call `Frame.addModule()`, like so:
```java
Frame.addModule(MyModule.class);
```

Frame handles all module instantiation in order to have a unified instance across the framework. If you wish to get hold of this instance, you can use `ModuleLoader.getInstance(<Module Class>)`.

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

We've now registered a command! If you built your plugin into a JAR and ran it on a server alongside Frame, running "/hello" would respond "Hello, world!"

In future I'll write more into this section...
