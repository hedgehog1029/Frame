# Frame [![Discord](https://img.shields.io/badge/discord-join-7286da.svg)](https://discord.gg/PWNHf)
[![CircleCI](https://img.shields.io/circleci/project/hedgehog1029/Frame.svg)](https://circleci.com/gh/hedgehog1029/Frame/)
[![Maven](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.offbeatwit.ch%2Frepository%2Freleases%2Fio%2Fgithub%2Fhedgehog1029%2Fframe%2Fmaven-metadata.xml)](https://maven.offbeatwit.ch/#artifact/io.github.hedgehog1029/frame)

A plugin framework for Spigot and Bukkit.

This branch (v1) is a complete overhaul of Frame. The whole library has
been rewritten from scratch to provide greater flexibility and decouple
the whole library from Bukkit, allowing it to be used anywhere.

If you're looking for support or just a place to talk about the plugin,
join the official [Scarlet Crystal Public Discord](https://discord.gg/PWNHf)!

# Server Owner Usage
* Frame by itself no longer runs as a plugin - a secondary layer, such as FrameBukkit, is now required.
* This means that developers can now use Frame in non-bukkit projects.

If you own a Spigot/Bukkit server and a plugin you use depends on
Frame, head over to the FrameBukkit repo for instructions. 

A Frame mod for Forge may be available in future.

# Developer Usage
If you're using Bukkit, you'll want to grab a FrameBukkit release instead.
(The release of this is pending! Just working on polishing it up.)

If you're using Frame in a standalone project, you can add it to your
Gradle configuration (or any other build system, really) via a Maven
dependency:

```groovy
repositories {
    maven {
        url = 'https://maven.offbeatwit.ch/repository/releases'
    }
}

dependencies {
    // among your other dependencies
    implementation "io.github.hedgehog1029:frame:1.4.1"
}
```


## Developer Guide

*This is a guide for using Frame in standalone mode; however, many
concepts apply to using Frame with an additional helper (such as
FrameBukkit) as well.*

To get started, you'll want to create a new `Frame.Builder` instance:

```java
// somewhere in your initialization code
Frame frame = new Frame.Builder()
    .setCommandFactory(new MyCommandFactory()) // we'll revisit this
    .build();

// our variable 'frame' now holds a brand new Frame instance
```

There's also the nicer Kotlin style, if you're using Kotlin:

```kotlin
val frame: Frame = Frame.Builder().apply {
    commandFactory = MyCommandFactory()
}.build()
```

`Frame.Builder` has all sorts of options you can set; in most cases,
you'll only want to use `setCommandFactory`, `setHookCallback` and
possibly `setLogReceiver`; the rest of the setters have sane defaults
and don't need to be called in the majority of applications.

You are **required** to set a command factory, but all other properties
are optional. We'll revisit command factories later in this guide.

### Modules

Modules are a core part of Frame; they're where you register commands
and use hooks. A "module" simply refers to a class which you instantiate
and pass to Frame; there's no interface to implement, so the following
is valid:

```java
class MyModule {
    // ...
}
```

To register this module, you simply need to call `Frame#loadModule`,
like so:

```java
frame.loadModule(new MyModule());
```

You've now successfully created and loaded a module! However, it
doesn't do anything yet; let's learn how to register a command next!

### Commands

Registering commands with Frame is super easy. Here's an example,
for a command which adds two numbers together:

```java
// our module class from before
class MyModule {
    @Command(aliases = "add", description = "Add two numbers.")
    private void echo(int first, int second) {
        System.out.println(first + second);
    }
}
```

Breaking this down, the `@Command` annotation is the most important part;
we specify the `aliases` (list of names this command goes by) and a
`description` (which is just some helpful text explaining what the
command does). The method name itself is irrelevant; the next important
part is the parameters.

This is the most powerful part of Frame - its parametric command
binding. In this example, our two parameters are of the `int` type -
they're numbers. This method signature `(int first, int second)` signals
to Frame that we expect two arguments, and they should both be valid
integers. Even though we call into Frame with String arguments, we can
simply write our command handling function to accept two integers,
and Frame will attempt to map the string input into valid integers to
invoke your handling method with. If you provide an input that can't be
mapped, Frame will throw an exception.

The beauty of this system is that it's extensible; Frame ships with
built-in support for primitives and strings, but that's just the tip
of the iceberg. By calling `Frame#loadBindings` with an instance
implementing `IBindingProvider`, you can write and register your own
provider classes, which tell Frame how to transform string inputs into
your custom type; for example, FrameBukkit registers a provider for the
`Player` class, which looks up a player by their username via the Bukkit
API and returns that if found.

This is all you need to do to register a command! Since we're in a
standalone environment we don't have any context arguments by default;
other environments such as FrameBukkit may make use of the @Sender
annotation to mark implicitly passed arguments.

#### Command factories

You may ask; where does this command actually get registered? The answer
is that your `ICommandFactory` implementor that you registered during
setup will be invoked with a shiny new command mapping instance
implementing `IPipeline` (after you call `Frame#go`). The reason that
Frame doesn't have an internal command registry is that it's designed to
work alongside existing systems, such as Bukkit or Forge's command map.
If you're running Frame standalone, you should store received pipelines
against all of their aliases (such as in a HashMap), and look up commands
from there when invoking.

```java
import io.github.hedgehog1029.frame.dispatcher.mapping.ICommandFactory;
import io.github.hedgehog1029.frame.dispatcher.pipeline.IPipeline;

class MyCommandFactory implements ICommandFactory {
    @Override
    public void registerCommand(IPipeline pipeline) {
        for (String alias : pipeline.getAliases()) {
            // do your registering here, for example:
            // SomeLibrary.register(alias, pipeline)
        }
    }
}
```

### Making everything work

The three steps to making Frame work are as follows:

1. Set up a Frame instance using `Frame.Builder`.
2. Call `Frame#loadModule` for each of your modules.
3. Call `Frame#go` after loading all of your modules.

Once you call `go()`, you'll receive all registered commands in your
command factory callback.
