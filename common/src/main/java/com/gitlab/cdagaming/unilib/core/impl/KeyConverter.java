/*
 * MIT License
 *
 * Copyright (c) 2018 - 2026 CDAGaming (cstack2011@yahoo.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.gitlab.cdagaming.unilib.core.impl;

import com.gitlab.cdagaming.unilib.core.CoreUtils;
import io.github.cdagaming.unicore.utils.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * KeyCode Conversion Layer used to translate between other Keyboard Data Types
 *
 * @author CDAGaming, deftware
 */
public class KeyConverter {
    /**
     * The Protocol Id marking the last LWJGL2-based Minecraft release (1.12.2)
     * <p>
     * Any protocol at or below this value uses LWJGL2's Keyboard KeyCode Schema
     */
    private static final int LEGACY_PROTOCOL_ID = 340;
    /**
     * The Protocol Id marking the first Minecraft release to use SDL for Input Handling, instead of GLFW
     * <p>
     * Any protocol at or above this value uses SDL's Scancode Schema
     */
    private static final int SDL_PROTOCOL_ID = 777;
    /**
     * Internal Mappings for all available KeyBinds within LWJGL
     */
    private static final List<KeyBindMapping> keyMappings = StringUtils.newArrayList(
            new KeyBindMapping(0, -1, 0, "None"),
            new KeyBindMapping(1, 256, 41, "Escape"),
            new KeyBindMapping(2, 49, 30, "1"),
            new KeyBindMapping(3, 50, 31, "2"),
            new KeyBindMapping(4, 51, 32, "3"),
            new KeyBindMapping(5, 52, 33, "4"),
            new KeyBindMapping(6, 53, 34, "5"),
            new KeyBindMapping(7, 54, 35, "6"),
            new KeyBindMapping(8, 55, 36, "7"),
            new KeyBindMapping(9, 56, 37, "8"),
            new KeyBindMapping(10, 57, 38, "9"),
            new KeyBindMapping(11, 48, 39, "0"),
            new KeyBindMapping(12, 45, 45, "Minus"),
            new KeyBindMapping(13, 61, 46, "Equals"),
            new KeyBindMapping(14, 259, 42, "Backspace"),
            new KeyBindMapping(15, 258, 43, "Tab"),
            new KeyBindMapping(16, 81, 20, "Q"),
            new KeyBindMapping(17, 87, 26, "W"),
            new KeyBindMapping(18, 69, 8, "E"),
            new KeyBindMapping(19, 82, 21, "R"),
            new KeyBindMapping(20, 84, 23, "T"),
            new KeyBindMapping(21, 89, 28, "Y"),
            new KeyBindMapping(22, 85, 24, "U"),
            new KeyBindMapping(23, 73, 12, "I"),
            new KeyBindMapping(24, 79, 18, "O"),
            new KeyBindMapping(25, 80, 19, "P"),
            new KeyBindMapping(26, 91, 47, "Left Bracket"),
            new KeyBindMapping(27, 93, 48, "Right Bracket"),
            new KeyBindMapping(28, 257, 40, "Return"),
            new KeyBindMapping(29, 341, 224, "Left Control"),
            new KeyBindMapping(30, 65, 4, "A"),
            new KeyBindMapping(31, 83, 22, "S"),
            new KeyBindMapping(32, 68, 7, "D"),
            new KeyBindMapping(33, 70, 9, "F"),
            new KeyBindMapping(34, 71, 10, "G"),
            new KeyBindMapping(35, 72, 11, "H"),
            new KeyBindMapping(36, 74, 13, "J"),
            new KeyBindMapping(37, 75, 14, "K"),
            new KeyBindMapping(38, 76, 15, "L"),
            new KeyBindMapping(39, 59, 51, "Semicolon"),
            new KeyBindMapping(40, 39, 52, "Apostrophe"),
            new KeyBindMapping(41, 96, 53, "Grave"),
            new KeyBindMapping(42, 340, 225, "Left Shift"),
            new KeyBindMapping(43, 92, 49, "Backslash"),
            new KeyBindMapping(44, 90, 29, "Z"),
            new KeyBindMapping(45, 88, 27, "X"),
            new KeyBindMapping(46, 67, 6, "C"),
            new KeyBindMapping(47, 86, 25, "V"),
            new KeyBindMapping(48, 66, 5, "B"),
            new KeyBindMapping(49, 78, 17, "N"),
            new KeyBindMapping(50, 77, 16, "M"),
            new KeyBindMapping(51, 44, 54, "Comma"),
            new KeyBindMapping(52, 46, 55, "Period"),
            new KeyBindMapping(53, 47, 56, "Slash"),
            new KeyBindMapping(54, 344, 229, "Right Shift"),
            new KeyBindMapping(55, 332, 85, "Keypad - Multiply"),
            new KeyBindMapping(56, 342, 226, "Left Alt"),
            new KeyBindMapping(57, 32, 44, "Space"),
            new KeyBindMapping(58, 280, 57, "Caps Lock"),
            new KeyBindMapping(59, 290, 58, "F1"),
            new KeyBindMapping(60, 291, 59, "F2"),
            new KeyBindMapping(61, 292, 60, "F3"),
            new KeyBindMapping(62, 293, 61, "F4"),
            new KeyBindMapping(63, 294, 62, "F5"),
            new KeyBindMapping(64, 295, 63, "F6"),
            new KeyBindMapping(65, 296, 64, "F7"),
            new KeyBindMapping(66, 297, 65, "F8"),
            new KeyBindMapping(67, 298, 66, "F9"),
            new KeyBindMapping(68, 299, 67, "F10"),
            new KeyBindMapping(69, 282, 83, "Number Lock"),
            new KeyBindMapping(70, 281, 71, "Scroll Lock"),
            new KeyBindMapping(71, 327, 95, "Keypad - 7"),
            new KeyBindMapping(72, 328, 96, "Keypad - 8"),
            new KeyBindMapping(73, 329, 97, "Keypad - 9"),
            new KeyBindMapping(74, 333, 86, "Keypad - Subtract"),
            new KeyBindMapping(75, 324, 92, "Keypad - 4"),
            new KeyBindMapping(76, 325, 93, "Keypad - 5"),
            new KeyBindMapping(77, 326, 94, "Keypad - 6"),
            new KeyBindMapping(78, 334, 87, "Keypad - Add"),
            new KeyBindMapping(79, 321, 89, "Keypad - 1"),
            new KeyBindMapping(80, 322, 90, "Keypad - 2"),
            new KeyBindMapping(81, 323, 91, "Keypad - 3"),
            new KeyBindMapping(82, 320, 98, "Keypad - 0"),
            new KeyBindMapping(83, 330, 99, "Keypad - Decimal"),
            new KeyBindMapping(87, 300, 68, "F11"),
            new KeyBindMapping(88, 301, 69, "F12"),
            new KeyBindMapping(100, 302, 104, "F13"),
            new KeyBindMapping(101, 303, 105, "F14"),
            new KeyBindMapping(102, 304, 106, "F15"),
            new KeyBindMapping(103, 305, 107, "F16"),
            new KeyBindMapping(104, 306, 108, "F17"),
            new KeyBindMapping(105, 307, 109, "F18"),
            new KeyBindMapping(113, 308, 110, "F19"),
            new KeyBindMapping(141, 336, 103, "Keypad - Equals"),
            new KeyBindMapping(156, 335, 88, "Keypad - Enter"),
            new KeyBindMapping(157, 345, 228, "Right Control"),
            new KeyBindMapping(181, 331, 84, "Keypad - Divide"),
            new KeyBindMapping(184, 346, 230, "Right Alt"),
            new KeyBindMapping(197, 284, 72, "Pause"),
            new KeyBindMapping(199, 268, 74, "Home"),
            new KeyBindMapping(200, 265, 82, "Up Arrow"),
            new KeyBindMapping(201, 266, 75, "Page Up"),
            new KeyBindMapping(203, 263, 80, "Left Arrow"),
            new KeyBindMapping(205, 262, 79, "Right Arrow"),
            new KeyBindMapping(207, 269, 77, "End"),
            new KeyBindMapping(208, 264, 81, "Down Arrow"),
            new KeyBindMapping(209, 267, 78, "Page Down"),
            new KeyBindMapping(210, 260, 73, "Insert"),
            new KeyBindMapping(211, 261, 76, "Delete"),
            new KeyBindMapping(219, 343, 227, "Left Meta"),
            new KeyBindMapping(220, 347, 231, "Right Meta")
    );
    /**
     * Internal Mappings for all unique KeyBinds within LWJGL 2
     * <p>
     * Note: These Keys largely originate from legacy JIS/business keyboards and do not have
     * a reliable or standardized SDL Scancode equivalent, and are treated as Unknown (0) under SDL
     */
    private static final List<KeyBindMapping> lwjgl2KeyMappings = StringUtils.newArrayList(
            new KeyBindMapping(112, -1, 0, "Kana"),
            new KeyBindMapping(121, -1, 0, "Convert"),
            new KeyBindMapping(123, -1, 0, "NoConvert"),
            new KeyBindMapping(125, -1, 0, "Symbol - Yen"),
            new KeyBindMapping(144, -1, 0, "Symbol - Circumflex"),
            new KeyBindMapping(145, -1, 0, "Symbol - At"),
            new KeyBindMapping(146, -1, 0, "Symbol - Colon"),
            new KeyBindMapping(147, -1, 0, "Underline"),
            new KeyBindMapping(148, -1, 0, "Kanji"),
            new KeyBindMapping(149, -1, 0, "Stop"),
            new KeyBindMapping(150, -1, 0, "AX"),
            new KeyBindMapping(151, -1, 0, "Unlabeled"),
            new KeyBindMapping(179, -1, 0, "Keypad - Comma"),
            new KeyBindMapping(183, -1, 0, "SysRq"),
            new KeyBindMapping(196, -1, 0, "Function"),
            new KeyBindMapping(221, -1, 0, "Apps"),
            new KeyBindMapping(222, -1, 0, "Power"),
            new KeyBindMapping(223, -1, 0, "Sleep")
    );
    /**
     * Mapping from lwjgl2 to lwjgl3
     * Note: Characters that are Unavailable in lwjgl3 are listed as lwjgl3's Unknown Keycode (-1)
     * Format: LWJGL2 Key;KeyMapping
     */
    public static final Map<Integer, KeyBindMapping> toGlfw = generateKeyStream(keyMappings, lwjgl2KeyMappings)
            .collect(Collectors.toMap(KeyBindMapping::lwjgl2Key, mapping -> mapping));
    /**
     * Internal Mappings for all unique KeyBinds within LWJGL 3
     */
    private static final List<KeyBindMapping> lwjgl3KeyMappings = StringUtils.newArrayList(
            new KeyBindMapping(0, 161, 0, "WORLD_1"),
            new KeyBindMapping(0, 162, 0, "WORLD_2"),
            new KeyBindMapping(0, 283, 70, "Print Screen"),
            new KeyBindMapping(0, 309, 111, "F20"),
            new KeyBindMapping(0, 310, 112, "F21"),
            new KeyBindMapping(0, 311, 113, "F22"),
            new KeyBindMapping(0, 312, 114, "F23"),
            new KeyBindMapping(0, 313, 115, "F24"),
            new KeyBindMapping(0, 314, 0, "F25"),
            new KeyBindMapping(0, 348, 101, "KEY_MENU")
    );
    /**
     * Mapping from lwjgl3 to lwjgl2
     * Note: Characters that are Unavailable in lwjgl2 are listed as lwjgl2's Unknown Keycode (0)
     * Format: LWJGL3 Key;KeyMapping
     */
    public static final Map<Integer, KeyBindMapping> fromGlfw = generateKeyStream(keyMappings, lwjgl3KeyMappings)
            .collect(Collectors.toMap(KeyBindMapping::lwjgl3Key, mapping -> mapping));
    /**
     * Mapping from any known SDL Scancode to its KeyBind data
     * <p>
     * Note: Entries without a reliable SDL equivalent share the Unknown Scancode (0), and
     * collapse onto the first such entry ("None") when queried, mirroring the Unknown handling
     * used by {@link #toGlfw} and {@link #fromGlfw}
     * <p>
     * Format: SDL Scancode;KeyMapping
     */
    public static final Map<Integer, KeyBindMapping> fromSdl = generateKeyStream(keyMappings, lwjgl3KeyMappings)
            .collect(Collectors.toMap(KeyBindMapping::sdlKey, mapping -> mapping, (first, second) -> first));

    /**
     * Filter Mappings for any LWJGL2 Keys, dependent on Protocol ID
     * <p>
     * Format: keyCode:condition
     */
    private static final Map<Integer, Predicate<Integer>> lwjgl2KeyConditions = StringUtils.newHashMap();

    /**
     * Filter Mappings for any LWJGL3 Keys, dependent on Protocol ID
     * <p>
     * Format: keyCode:condition
     */
    private static final Map<Integer, Predicate<Integer>> lwjgl3KeyConditions = StringUtils.newHashMap();

    /**
     * Filter Mappings for any SDL Keys, dependent on Protocol ID
     * <p>
     * Format: keyCode:condition
     */
    private static final Map<Integer, Predicate<Integer>> sdlKeyConditions = StringUtils.newHashMap();

    /**
     * KeyCodes that when pressed will be interpreted as NONE/UNKNOWN
     * After ESC and Including any KeyCodes under 0x00
     * <p>
     * Notes:
     * LWJGL 2: ESC = 0x01
     * LWJGL 3 (GLFW): ESC = 256
     * LWJGL 3 (SDL): ESC == 41
     */
    private static final List<Integer> lwjgl2ClearKeys = StringUtils.newArrayList(
            1 // Escape
    );

    /**
     * KeyCodes that when pressed will be interpreted as NONE/UNKNOWN
     * After ESC and Including any KeyCodes under 0x00
     * <p>
     * Notes:
     * LWJGL 2: ESC = 0x01
     * LWJGL 3 (GLFW): ESC = 256
     * LWJGL 3 (SDL): ESC == 41
     */
    private static final List<Integer> lwjgl3ClearKeys = StringUtils.newArrayList(
            256 // Escape
    );

    /**
     * KeyCodes that when pressed will be interpreted as NONE/UNKNOWN
     * After ESC and Including any KeyCodes under 0x00
     * <p>
     * Notes:
     * LWJGL 2: ESC = 0x01
     * LWJGL 3 (GLFW): ESC = 256
     * LWJGL 3 (SDL): ESC == 41
     */
    private static final List<Integer> sdlClearKeys = StringUtils.newArrayList(
            41 // Escape
    );

    /**
     * Generate a combined {@link KeyBindMapping} stream
     *
     * @param mappings The primary mappings to use (Required)
     * @param extras   The extra mappings to use (Optional)
     * @return the processed stream
     */
    private static Stream<KeyBindMapping> generateKeyStream(final List<KeyBindMapping> mappings, final List<KeyBindMapping> extras) {
        final List<KeyBindMapping> results = StringUtils.newArrayList(mappings);
        if (extras != null && !extras.isEmpty()) {
            results.addAll(extras);
        }
        return results.stream();
    }

    /**
     * Generate a combined {@link KeyBindMapping} stream
     *
     * @param mappings The primary mappings to use (Required)
     * @return the processed stream
     */
    private static Stream<KeyBindMapping> generateKeyStream(final List<KeyBindMapping> mappings) {
        return generateKeyStream(mappings, null);
    }

    /**
     * Determine the Native Keyboard Platform in use for the specified Protocol
     *
     * @param protocol The protocol to Target for this operation
     * @return the resulting {@link Platform}
     */
    public static Platform getPlatform(final int protocol) {
        if (protocol >= SDL_PROTOCOL_ID) {
            return Platform.SDL;
        } else if (protocol > LEGACY_PROTOCOL_ID) {
            return Platform.LWJGL3;
        } else {
            return Platform.LWJGL2;
        }
    }

    /**
     * Determine if the Source KeyCode fulfills the following conditions
     * <p>
     * 1) Is Not Contained or falls under a valid key condition mapping
     *
     * @param sourceKeyCode The Source KeyCode to Check
     * @param protocol      The protocol to Target for this operation
     * @return {@link Boolean#TRUE} if and only if a Valid KeyCode
     */
    public static boolean isValidKeyCode(final int sourceKeyCode, final int protocol) {
        final Map<Integer, Predicate<Integer>> keyConditions = switch (getPlatform(protocol)) {
            case SDL -> sdlKeyConditions;
            case LWJGL3 -> lwjgl3KeyConditions;
            case LWJGL2 -> lwjgl2KeyConditions;
        };
        if (keyConditions.containsKey(sourceKeyCode)) {
            return keyConditions.get(sourceKeyCode).test(protocol);
        }
        return true;
    }

    /**
     * Determine if the Source KeyCode fulfills the following conditions
     * <p>
     * 1) Is Not Contained or Listed within clearKeys mapping
     *
     * @param sourceKeyCode The Source KeyCode to Check
     * @param protocol      The protocol to Target for this operation
     * @return {@link Boolean#TRUE} if and only if a Valid KeyCode
     */
    public static boolean isValidClearCode(final int sourceKeyCode, final int protocol) {
        final List<Integer> clearKeys = switch (getPlatform(protocol)) {
            case SDL -> sdlClearKeys;
            case LWJGL3 -> lwjgl3ClearKeys;
            case LWJGL2 -> lwjgl2ClearKeys;
        };
        return clearKeys.contains(sourceKeyCode);
    }

    /**
     * Determine the LWJGL KeyCode Name for the inputted KeyCode
     *
     * @param original A KeyCode, in Integer Form
     * @param protocol The protocol to Target for this operation
     * @param fallback The function to fallback on, if failed to get a name
     * @return Either an LWJGL KeyCode Name or the KeyCode if none can be found
     */
    public static String getKeyName(final int original, final int protocol, final BiFunction<Integer, Integer, String> fallback) {
        final Platform platform = getPlatform(protocol);
        final int unknownKeyCode = platform == Platform.LWJGL2 ? -1 : 0;
        final String unknownKeyName = (platform == Platform.LWJGL2 ? fromGlfw : toGlfw).get(unknownKeyCode).name();
        if (isValidKeyCode(original, protocol)) {
            // If Input is a valid Integer and Valid KeyCode,
            // Parse depending on Protocol
            final Map<Integer, KeyBindMapping> mappings = switch (platform) {
                case SDL -> fromSdl;
                case LWJGL3 -> fromGlfw;
                case LWJGL2 -> toGlfw;
            };
            if (mappings.containsKey(original)) {
                return mappings.get(original).name();
            } else if (fallback != null && original != unknownKeyCode) {
                // If no other Mapping Layer contains the KeyCode Name,
                // fallback to alternative methods to retrieve the KeyCode Name
                return StringUtils.getOrDefault(fallback.apply(original, protocol), Integer.toString(original));
            } else {
                return unknownKeyName;
            }
        }
        // If Not a Valid KeyCode, return the appropriate Unknown Keycode
        return unknownKeyName;
    }

    /**
     * Converts a KeyCode using the Specified Conversion Mode, if possible
     * <p>
     * Note: If None is Used on a Valid Value, this function can be used as verification, if any
     * <p>
     * Note: {@code originalProtocol} determines which Native Keyboard Platform {@code originalKey} is
     * already encoded in (e.g. an LWJGL2 KeyCode, a LWJGL3/GLFW Keycode, or an SDL Scancode), so any
     * pairing of source and target Platform, such as LWJGL2 to SDL, or LWJGL3/GLFW to SDL, converts
     * directly in a single call
     *
     * @param originalKey      The original Key to Convert
     * @param originalProtocol The Protocol the original key derives from
     * @param targetProtocol   The Protocol to Target for this conversion
     * @param mode             The Conversion Mode to convert the keycode to
     * @return The resulting converted KeyCode, or the mode's unknown key
     */
    public static int convertKey(final int originalKey, final int originalProtocol, final int targetProtocol, final ConversionMode mode) {
        final Platform sourcePlatform = getPlatform(originalProtocol);
        final Map<Integer, KeyBindMapping> sourceMap = switch (sourcePlatform) {
            case LWJGL2 -> toGlfw;
            case LWJGL3 -> fromGlfw;
            case SDL -> fromSdl;
        };
        final KeyBindMapping unknownKeyData = mode == ConversionMode.Lwjgl2 ? fromGlfw.get(-1) : toGlfw.get(0);

        int resultKey;
        if (mode == ConversionMode.Lwjgl2) {
            resultKey = sourceMap.getOrDefault(originalKey, unknownKeyData).lwjgl2Key();
        } else if (mode == ConversionMode.Lwjgl3) {
            resultKey = sourceMap.getOrDefault(originalKey, unknownKeyData).lwjgl3Key();
        } else if (mode == ConversionMode.Sdl) {
            resultKey = sourceMap.getOrDefault(originalKey, unknownKeyData).sdlKey();
        } else if (mode == ConversionMode.None) {
            // If Input is a valid Integer and Valid KeyCode within its own (origin) Protocol,
            // Retain the Original Value
            resultKey = sourceMap.containsKey(originalKey) ? originalKey : (originalProtocol <= LEGACY_PROTOCOL_ID ? -1 : 0);
        } else {
            resultKey = (targetProtocol <= LEGACY_PROTOCOL_ID ? -1 : 0);
        }

        if (resultKey == originalKey && mode != ConversionMode.None) {
            CoreUtils.LOG.warn(
                    "Unexpected KeyConverter result for object \"%1$s\". Please report this issue. (Mode Attempted: %2$s)",
                    Integer.toString(resultKey), mode.name()
            );
        }

        return resultKey;
    }

    /**
     * A Mapping storing the possible Native Keyboard Platforms recognized by this module
     */
    public enum Platform {
        /**
         * Constant for the "LWJGL2" Platform. (Legacy Minecraft, up to and including 1.12.2)
         */
        LWJGL2,
        /**
         * Constant for the "LWJGL3" Platform. (GLFW-based Minecraft, from 1.13 up to 26.2)
         */
        LWJGL3,
        /**
         * Constant for the "SDL" Platform. (SDL-based Minecraft, from 26.3 onward)
         */
        SDL
    }

    /**
     * A Mapping storing the possible Conversion Modes for this module
     */
    public enum ConversionMode {
        /**
         * Constant for the "LWJGL2" Conversion Mode.
         */
        Lwjgl2,
        /**
         * Constant for the "LWJGL3" Conversion Mode.
         */
        Lwjgl3,
        /**
         * Constant for the "SDL" Conversion Mode.
         */
        Sdl,
        /**
         * Constant for the "None" Conversion Mode.
         */
        None,
        /**
         * Constant for the "Unknown" Conversion Mode.
         */
        Unknown
    }

    /**
     * A Mapping for KeyBind data across different Native Keyboard Platforms
     *
     * @param lwjgl2Key The KeyBind representation for LWJGL2
     * @param lwjgl3Key The KeyBind representation for LWJGL3 (GLFW Keycode)
     * @param sdlKey    The KeyBind representation for SDL (SDL Scancode)
     * @param name      The name of the KeyBind
     */
    public record KeyBindMapping(int lwjgl2Key, int lwjgl3Key, int sdlKey, String name) {
    }

}
