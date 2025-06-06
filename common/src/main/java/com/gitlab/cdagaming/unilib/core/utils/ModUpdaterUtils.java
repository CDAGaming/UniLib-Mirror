/*
 * MIT License
 *
 * Copyright (c) 2018 - 2025 CDAGaming (cstack2011@yahoo.com)
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

package com.gitlab.cdagaming.unilib.core.utils;

import com.gitlab.cdagaming.unilib.core.CoreUtils;
import io.github.cdagaming.unicore.integrations.versioning.VersionComparator;
import io.github.cdagaming.unicore.utils.FileUtils;
import io.github.cdagaming.unicore.utils.StringUtils;
import io.github.cdagaming.unicore.utils.UrlUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Set of Utilities used for Retrieving and Alerting on Any Mod Updates
 *
 * @author CDAGaming
 */
public class ModUpdaterUtils {
    /**
     * The MOD ID attached to this Mod Updater Instance
     */
    private final String modID;
    /**
     * The Update URL to retrieve Updates from in this Instance
     */
    private final String updateUrl;
    /**
     * The Changelog Data attached to the Target Version, if any
     */
    private final Map<String, String> changelogData = StringUtils.newLinkedHashMap();
    /**
     * The Current Version attached to this Instance
     */
    private final String currentVersion;
    /**
     * The Current Game Version attached to this Instance
     */
    private final String currentGameVersion;
    /**
     * The Current Update State for this Mod Updater Instance
     */
    private UpdateState currentState = UpdateState.PENDING;
    /**
     * The Download Url, otherwise known as the "homepage" element in the Json
     */
    private String downloadUrl;
    /**
     * The Target Latest/Unstable Version for this Instance, based on retrieved data
     */
    private String targetLatestVersion;
    /**
     * The Target Recommended/Stable Version for this Instance, based on retrieved data
     */
    private String targetRecommendedVersion;
    /**
     * The Target Main Version for this Instance, dependent on Update State
     */
    private String targetVersion;

    /**
     * Initializes this Module from the Specified Arguments
     *
     * @param modID              The Mod ID for this Instance
     * @param updateUrl          The Update Url to attach to this Instance
     * @param currentVersion     The Current Version to attach to this Instance
     * @param currentGameVersion The Current Game Version to attach to this Instance
     */
    public ModUpdaterUtils(final String modID, final String updateUrl, final String currentVersion, final String currentGameVersion) {
        this.modID = modID;
        this.updateUrl = updateUrl;
        this.currentGameVersion = currentGameVersion;
        this.currentVersion = currentVersion;
    }

    /**
     * Clears Runtime Client Data from this Module
     */
    public void flushData() {
        currentState = UpdateState.PENDING;
        targetRecommendedVersion = "";
        targetLatestVersion = "";
        targetVersion = "";
        downloadUrl = "";
        changelogData.clear();
    }

    /**
     * Retrieve the current {@link UpdateState} from this Module
     *
     * @return the current {@link UpdateState}
     */
    public UpdateState getStatus() {
        return currentState;
    }

    /**
     * Retrieve the Download Url, otherwise known as the "homepage" element in the Json
     *
     * @return the download url for this module
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * Retrieve whether any changelog data exists for this module
     *
     * @return {@link Boolean#TRUE} if condition is satisfied
     */
    public boolean hasChanges() {
        return !changelogData.isEmpty();
    }

    /**
     * Retrieve the changelog data attached to this module
     *
     * @return the changelog data for this module
     */
    public Set<Map.Entry<String, String>> getChanges() {
        return changelogData.entrySet();
    }

    /**
     * Checks for Updates, updating the Current Update Check State
     */
    public void checkForUpdates() {
        checkForUpdates(null);
    }

    /**
     * Checks for Updates, updating the Current Update Check State
     *
     * @param callback The callback to run after Update Events
     */
    public void checkForUpdates(final Runnable callback) {
        CoreUtils.getThreadFactory().newThread(() -> process(callback)).start();
    }

    private void process(final Runnable callback) {
        try {
            flushData();

            if (StringUtils.isNullOrEmpty(updateUrl)) return;
            CoreUtils.LOG.info(
                    "Starting version check for \"%1$s\" (MC %2$s) at \"%3$s\"",
                    modID, currentGameVersion, updateUrl
            );

            final String data = UrlUtils.getURLText(updateUrl, "UTF-8");

            CoreUtils.LOG.debugInfo("Received update data:\\n%1$s", data);

            @SuppressWarnings("unchecked") final Map<String, Object> json = FileUtils.getJsonData(data, Map.class);
            @SuppressWarnings("unchecked") final Map<String, String> promos = (Map<String, String>) json.get("promos");
            downloadUrl = (String) json.get("homepage");

            targetRecommendedVersion = promos.get(currentGameVersion + "-recommended");
            targetLatestVersion = promos.get(currentGameVersion + "-latest");

            final VersionComparator cmp = new VersionComparator();
            final boolean hasRecommended = !StringUtils.isNullOrEmpty(targetRecommendedVersion);
            final boolean hasLatest = !StringUtils.isNullOrEmpty(targetLatestVersion);

            if (hasRecommended) {
                final int diff = cmp.compare(targetRecommendedVersion, currentVersion);

                if (diff == 0)
                    currentState = UpdateState.UP_TO_DATE;
                else if (diff < 0) {
                    currentState = UpdateState.AHEAD;
                    if (hasLatest && cmp.compare(currentVersion, targetLatestVersion) < 0) {
                        currentState = UpdateState.OUTDATED;
                        targetVersion = targetLatestVersion;
                    }
                } else {
                    currentState = UpdateState.OUTDATED;
                    targetVersion = targetRecommendedVersion;
                }
            } else if (hasLatest) {
                if (cmp.compare(currentVersion, targetLatestVersion) < 0)
                    currentState = UpdateState.BETA_OUTDATED;
                else
                    currentState = UpdateState.BETA;
                targetVersion = targetLatestVersion;
            } else
                currentState = UpdateState.BETA;

            CoreUtils.LOG.info(
                    "Received update status for \"%1$s\" -> %2$s (Target version: \"%3$s\")",
                    modID, currentState.getDisplayName(), targetVersion
            );

            changelogData.clear();
            @SuppressWarnings("unchecked") final Map<String, String> tmp = (Map<String, String>) json.get(currentGameVersion);
            if (tmp != null) {
                final List<String> ordered = StringUtils.newArrayList();
                for (String key : tmp.keySet()) {
                    if (cmp.compare(key, currentVersion) > 0 && (StringUtils.isNullOrEmpty(targetVersion) || cmp.compare(key, targetVersion) < 1)) {
                        ordered.add(key);
                    }
                }
                Collections.sort(ordered);

                for (String ver : ordered) {
                    changelogData.put(ver, tmp.get(ver));
                }
            }
        } catch (Throwable ex) {
            // Log Failure and Set Update State to FAILED
            CoreUtils.LOG.error("Failed to check for updates, enable debug mode to see errors...");
            CoreUtils.LOG.debugError(ex);
            currentState = UpdateState.FAILED;
        } finally {
            if (callback != null) {
                callback.run();
            }
        }
    }

    /**
     * Mapping for CFU State (Based on <a href="https://docs.minecraftforge.net/en/latest/misc/updatechecker/">Forge Systems</a>)
     *
     * <p>FAILED: The version checker could not connect to the URL provided.
     * <p>UP_TO_DATE: The current version is equal to the recommended version.
     * <p>AHEAD: The current version is newer than the recommended version if there is not latest version.
     * <p>OUTDATED: There is a new recommended or latest version.
     * <p>BETA_OUTDATED: There is a new latest version.
     * <p>BETA: The current version is equal to or newer than the latest version.
     * <p>PENDING: The result requested has not finished yet, so you should try again in a little bit.
     */
    public enum UpdateState {
        /**
         * The CFU State representing a "Failed" status
         */
        FAILED,
        /**
         * The CFU State representing an "Up to Date" status
         */
        UP_TO_DATE("Release"),
        /**
         * The CFU State representing an "Ahead" status
         */
        AHEAD,
        /**
         * The CFU State representing an "Outdated" status
         */
        OUTDATED,
        /**
         * The CFU State representing an "Outdated Beta" status
         */
        BETA_OUTDATED("Beta (Outdated)"),
        /**
         * The CFU State representing a "Beta" status
         */
        BETA,
        /**
         * The CFU State representing a "Pending" status
         */
        PENDING;

        private final String displayName;

        UpdateState() {
            displayName = StringUtils.formatWord(name().toLowerCase());
        }

        UpdateState(final String displayName) {
            this.displayName = displayName;
        }

        /**
         * Retrieves the display name for the specified {@link UpdateState}
         *
         * @return The display name corresponding to the {@link UpdateState}
         */
        public String getDisplayName() {
            return displayName;
        }
    }
}
