# UniLib Changes

## v1.1.1 (08/07/2025)

_A Detailed Changelog from the last release is
available [here](https://gitlab.com/CDAGaming/UniLib/-/compare/release%2Fv1.1.0...release%2Fv1.1.1)_

See the Mod Description or [README](https://gitlab.com/CDAGaming/UniLib) for more info regarding the mod.

### Changes

* (Backend) Updated Build Dependencies (Please see the appropriate repositories for changes)
    * Spotless (`7.0.3` -> `7.2.1`)
    * UniCore (`1.3.2` -> `1.3.3`)
    * Shadow (`8.3.6` -> `8.3.8`)
    * Gradle (`8.13` -> `8.14.3`)
    * Classgraph (`4.8.179` -> `4.8.181`)
    * Unimined (`1.3.14` -> `1.3.15`)
    * ASM (`9.7.1` -> `9.8`)
* Removed Quilt and FlintMC Support due to end-of-life conditions
    * Quilt users can continue to be supported via the existing Fabric Port

### Fixes

* Fixed an AW crash related to `RenderUtils` APIs on Neoforge 1.21.6 and above
* Fixed a potential crash with translation listener events on Neoforge `21.6.6-beta` and above
    * The minimum neoforge requirement on `1.21.6` has been bumped due to this fix

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
