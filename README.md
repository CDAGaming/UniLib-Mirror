# UniLib

A common set of Utilities, designed for over 50 versions of Minecraft!

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/5e0667f7208b49ecab1a6affbfa6cbf7)](https://app.codacy.com/gl/CDAGaming/CraftPresence/dashboard?utm_source=gl&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Pipeline Status](https://github.com/CDAGaming/UniLib-Mirror/actions/workflows/build.yml/badge.svg?branch=master)](https://gitlab.com/CDAGaming/UniLib/commits/master)

[![CurseForge-Downloads](https://cf.way2muchnoise.eu/full_unilib_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/unilib)
[![CurseForge-Availability](https://cf.way2muchnoise.eu/versions/unilib.svg)](https://www.curseforge.com/minecraft/mc-mods/unilib)

[![Modrinth-Downloads](https://img.shields.io/modrinth/dt/nT86WUER)](https://modrinth.com/mod/unilib)
[![Modrinth-Availability](https://img.shields.io/modrinth/game-versions/nT86WUER)](https://modrinth.com/mod/unilib)

## General Notes

* This mod identifies as a **Client Side-only** mod
    * This means it **will not run** on the Server's side.
    * Fabric and Quilt mod loaders will simply ignore the
      mod, while other mod loaders may crash.
* Some versions of the mod for Minecraft 1.14.x and above require
  the [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
  and the [Fabric mod loader](https://fabricmc.net/use/installer)
* Some versions of the mod for Minecraft 1.13.x require
  the [Rift API](https://www.curseforge.com/minecraft/mc-mods/rift)
  and the [Rift mod loader](https://github.com/DimensionalDevelopment/Rift/releases)
* Versions of the mod for Minecraft 1.1.0 and below
  require [Risugami's ModLoader](https://mcarchive.net/mods/modloader)

## Features

TBD

## Disclaimers & Additional Info

### Minecraft Issues + Additional Build Info

Despite best efforts, issues can occur due to the state of Mojang's Codebase.

These issues can hinder certain portions of the backend in addition to cause certain parts of the mod to not work.

With this in mind, please note the following:

* **Minecraft 1.16 and above**
    * As more parts of the game become data-driven, some modded data is no longer able to be automatically retrieved
      without first being in the world.
* **Minecraft 1.15 and below**
    * `MC-112292`: When interacting with the `RenderUtils#drawItemStack` method, used in the v2 Item Renderer, blocks
      using certain renderers may fail to display properly.
    * Additionally, on 1.15.x exclusively, z-level issues may occur on Screens using this method
* **Miscellaneous Issues**
    * Due to obfuscation issues in earlier versions of Minecraft, incorrect data may appear when using certain parts of
      the mod.

Additionally, some settings or API calls may perform differently under certain MC versions.

### Support

Need some assistance with one of my mods or wish to provide feedback?

I can be contacted via the following methods:

* [Email](mailto:cstack2011@yahoo.com)
* [CurseForge](https://www.curseforge.com/minecraft/mc-mods/unilib)
* [Discord :: ![Discord Chat](https://img.shields.io/discord/455206084907368457.svg)](https://discord.com/invite/BdKkbpP)

Additionally, codebase documentation for this mod is
available [here](https://cdagaming.gitlab.io/unilib-documentation/) with further guides available
on [the wiki](https://gitlab.com/CDAGaming/UniLib/-/wikis/Home)

#### Licensing

This Mod is under the MIT License as well as the Apache 2.0 License

This project makes usage of the following dependencies internally:

* [Classgraph](https://github.com/classgraph/classgraph) by [lukehutch](https://github.com/lukehutch)
* [Lenni Reflect](https://github.com/Lenni0451/Reflect) by [Lenni0451](https://github.com/Lenni0451)
