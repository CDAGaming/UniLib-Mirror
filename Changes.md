# UniLib Changes

## v1.0.6 (04/21/2025)

_A Detailed Changelog from the last release is
available [here](https://gitlab.com/CDAGaming/UniLib/-/compare/release%2Fv1.0.5...release%2Fv1.0.6)_

See the Mod Description or [README](https://gitlab.com/CDAGaming/UniLib) for more info regarding the mod.

### Changes

* (Backend) Updated Build Dependencies (Please see the appropriate repositories for changes)
    * Gradle (`8.12` -> `8.13`)
    * Unimined (`1.3.12` -> `1.3.14`)
    * JVMDowngrader (`1.2.1` -> `1.2.2`)
    * Spotless (`6.25.0` -> `7.0.2`)
    * Shadow (`8.2.5` -> `8.3.6`)
    * Fabric Loader (`0.16.9` -> `0.16.13`)
    * ImageIO-WebP
* Added support for WebP (Static and Animated) Images for `ImageUtils` APIs

### Fixes

* Fixed an issue with `ImageUtils#getTextureFromUrl` caching sometimes causing texture flickering
* Fixed a potential NPE error when initially rendering the background of an `ExtendedScreen` on MC 1.16 and above
* Fixed missing deferred tooltip support on MC 1.19.3 and above
* Fixed incorrect `BufferedImage->NativeImage` conversion for `ImageUtils` APIs on MC 1.21.2 and above
* Fixed an NPE with `ScrollableListControl` entry initialization

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
