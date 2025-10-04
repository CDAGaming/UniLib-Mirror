# UniLib Changes

## v1.2.0 (10/24/2025)

_A Detailed Changelog from the last release is
available [here](https://gitlab.com/CDAGaming/UniLib/-/compare/release%2Fv1.1.1...release%2Fv1.2.0)_

See the Mod Description or [README](https://gitlab.com/CDAGaming/UniLib) for more info regarding the mod.

### Changes

* (Backend) Updated Build Dependencies (Please see the appropriate repositories for changes)
    * Fabric Loader (`0.16.14` -> `0.17.2`)
    * Spotless (`7.2.1` -> `8.0.0`)
    * Shadow (`8.3.8` -> `9.2.2`)
    * ModPublisher (`2.1.6` -> `2.1.8`)
    * SpotBugs Annotations (`4.8.6` -> `4.9.6`)
* Backported several API changes from the `-Staging` builds, for various MC versions:
  * Implemented `mouseReleased` call in `ScrollPane`, allowing `clickedScrollbar` to become `false` when mouse is released
  * Added new APIs to `ScrollPane`: `setScrolling(bool)` and `isOverScrollbar(mouseX, mouseY)`
  * Added `rawCategoryName()` to `KeyUtils#KeyBindData` as an alias to `category()`
* Added additional API calls for the `PREINIT` and `INIT` phases of Screen Initialization
* Added `constructElements()` to `ExtendedScreen` as an alternative to using `initializeUi` for adding UI elements to the screen
  * This method, alongside the prior note, allow for initializing controls in the correct phase
  * Previously, double-rendering would occur in the first tick on sub-screens

### Fixes

* Fixed an incorrect initialization call in `ExtendedScreen#initializeUi`, causing a crash in certain scenarios

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
