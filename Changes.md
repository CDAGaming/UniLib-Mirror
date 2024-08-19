# UniLib Changes

## v1.0.2 (08/20/2024)

_A Detailed Changelog from the last release is
available [here](https://gitlab.com/CDAGaming/UniLib/-/compare/release%2Fv1.0.1...release%2Fv1.0.2)_

See the Mod Description or [README](https://gitlab.com/CDAGaming/UniLib) for more info regarding the mod.

### Changes

* (Backend) Updated Build Dependencies (Please see the appropriate repositories for changes)
    * JVMDowngrader (`1.0.0` -> `1.1.1`)
    * Shadow (`8.1.8` -> `8.3.0`)
    * Unimined (`1.3.4` -> `1.3.7`)
* `RenderUtils#drawTexture` now includes an optional `asFullTexture` param
    * This flag defaults to `true` and is used to toggle additional GL flags to replicate the behaviors
      of `drawGradient`
* Added additional flags to `ScreenConstants#ColorData` to allow additional control over its behavior
  in `ExtendedScreen`
    * `texLevel (Default: 0.0D)`
    * `colorLevel (Default: 300.0D)`
    * `useFullTexture (Default: true)`
    * `textureWidth (Default: 32.0D)`
    * `textureHeight (Default: 32.0D)`
    * Additional accessors have also been added for ease-of-access
* Several `ScrollPane` and `EntryListPane` changes have been made to improve resource pack support and for ease-of-usage
    * Added the ability to customize the `top`, `bottom`, and `height` definitions for the header and footer depth
      decorations
    * The Header and Footer depth decorations, as well as the Scrollbar elements, now all use customizable Texture
      rendering instead of Gradient rendering

### Fixes

* Fixed a publishing issue where `quilted_fabric_api` was not marked as `required`
    * Effected the 1.18.2+ builds on deployment platforms
* Fixed documentation errors in `ScreenConstants#ColorData`
* Fixed incorrect parameter type on `ExtendedScreen#drawBackground` with `drawGradient` calls
* Fixed an incorrect calculation for the Scrollbar top coordinate in `ScrollPane`
    * Also effects the `EntryListPane` implementation

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
