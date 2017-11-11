# Frame [![Discord](https://img.shields.io/badge/discord-join-7286da.svg)](https://discord.gg/PWNHf)
[![CircleCI](https://img.shields.io/circleci/project/hedgehog1029/Frame.svg)](https://circleci.com/gh/hedgehog1029/Frame/)

A plugin framework for Spigot and Bukkit.

This branch (v1) is a complete overhaul of Frame. The whole library has been rewritten from scratch
to provide greater flexibility and decouple the whole library from Bukkit, allowing it
to be used anywhere.

If you're looking for support or just a place to talk about the plugin, join the official [Scarlet Crystal Public Discord](https://discord.gg/PWNHf)!

# Server Owner Usage
* Frame by itself no longer runs as a plugin - a secondary layer, such as FrameBukkit, is now required.
* This means that developers can now use Frame in non-bukkit projects.

If you own a Spigot/Bukkit server and a plugin you use depends on Frame, head over to the FrameBukkit repo for instructions. 

A Frame mod for Forge may be available in future.

# Developer Usage
If you're using Bukkit, you'll want to grab a FrameBukkit release instead. 

If you're using Frame in a standalone project, you will be able to add it as a  Gradle dependency when I get around to figuring that out.

### Where Did The Docs Go?

Frame is still in its early stages, so I haven't written up documentation for v1.
There will be docs here before release!
