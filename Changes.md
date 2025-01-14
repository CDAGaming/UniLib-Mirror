# UniLib Changes

## v1.0.5 (01/14/2025)

_A Detailed Changelog from the last release is
available [here](https://gitlab.com/CDAGaming/UniLib/-/compare/release%2Fv1.0.4...release%2Fv1.0.5)_

See the Mod Description or [README](https://gitlab.com/CDAGaming/UniLib) for more info regarding the mod.

### Changes

* (Backend) Updated Build Dependencies (Please see the appropriate repositories for changes)
    * UniCore (`1.2.6` -> `1.2.8`)
    * Gradle (`8.11` -> `8.12`)
    * Unimined (`1.3.9` -> `1.3.11`)

### Fixes

* (Backend) Fixed `ModUtils#getMinecraftInstance` failing from MC 1.2.5 to b1.1_02, if not using `MinecraftApplet`
* (Backend) Fixed `CoreUtils#MOD_COUNT_SUPPLIER` throwing a `ClassNotFound` exception on MC 1.6.4 and below with Fabric
* (Backend) Fixed an incorrect `fabric_loader_range`, causing `0.12.x` Fabric Loader issues even when compatible

___

### More Information

#### Known Issues

Despite compatibility often being ensured between versions,
caution is advised to ensure the best experience, while also baring in mind that features can be adjusted, removed, or
added/iterated upon between releases.

Please refer to the Mod Description or [README](https://gitlab.com/CDAGaming/UniLib) to view more info relating
to known issues.

#### Snapshot Build Info

Some Versions of this Mod are for Minecraft Snapshots or Experimental Versions, and as such, caution should be noted.

Any Snapshot Build released will be marked as **ALPHA** to match its Snapshot Status depending on tests done before
release
and issues found.

Snapshot Builds, depending on circumstances, may also contain changes for a future version of the mod, and will be noted
as so if this is the case with the `-Staging` label.
