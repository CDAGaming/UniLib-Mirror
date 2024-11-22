import xyz.wagyourtail.unimined.api.mapping.task.ExportMappingsTask
import xyz.wagyourtail.unimined.api.minecraft.task.RemapJarTask

/**
 * Retrieve a Project Property
 */
operator fun String.invoke(): String? {
    return project.properties[this] as String?
}

val modName: String by extra
val modId: String by extra

val isLegacy: Boolean by extra
val protocol: Int by extra
val isJarMod: Boolean by extra
val accessWidenerFile: File by extra
val isMCPJar: Boolean by extra
val isModern: Boolean by extra
val versionFormat: String by extra
val versionLabel: String by extra
val mcVersionLabel: String by extra
val mcMappingsType: String by extra
val canUseATs: Boolean by extra
val baseVersionLabel: String by extra

unimined.minecraft {
    // N/A
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating

configurations.compileClasspath.get().extendsFrom(common)
configurations.runtimeClasspath.get().extendsFrom(common)

dependencies {
    // Flint API Implementation
    "modImplementation"("net.flintloader:flint-api:${"flint_api_version"()}")
    // Mod Menu API Implementation
    "modImplementation"("${"modmenu_group"()}:ModMenu:${"modmenu_version"()}")

    "common"(project(path = ":common")) { isTransitive = false }
    "common"(project(path = ":common", configuration = "shade")) {
        exclude(group = "com.google.code.gson")
    }
    "common"(project(path = ":common", configuration = "runtime"))
    "shadowCommon"(project(path = ":common", configuration = "shadeOnly"))
    "shadowCommon"(project(path = ":common")) { isTransitive = false }
}

val resourceTargets = listOf(
    "flintmodule.json",
    "pack.mcmeta"
)
val replaceProperties = mapOf(
    "mod_id" to modId,
    "mod_name" to modName,
    "version" to baseVersionLabel,
    "mcversion" to mcVersionLabel,
    "loader_version_range" to "flint_loader_version_range"()
)

tasks.processResources {
    inputs.properties(replaceProperties)

    filesMatching(resourceTargets) {
        expand(replaceProperties)
    }

    filesMatching("mappings-flint.srg") {
        filter { line ->
            @Suppress("NULL_FOR_NONNULL_TYPE")
            if (line.startsWith("CL:")) line.replace("/", ".") else null
        }
    }
}

tasks.named<ExportMappingsTask>("exportMappings") {
    val target = if (isMCPJar) "searge" else (if (!isModern) "mcp" else "mojmap")
    export {
        setTargetNamespaces(listOf(target))
        setSourceNamespace(if (isJarMod) "official" else "intermediary")
        location = file("$projectDir/src/main/resources/mappings-flint.srg")
        setType("SRG")
    }
}
tasks.processResources.get().dependsOn(tasks.named("exportMappings"))

tasks.shadowJar {
    mustRunAfter(project(":common").tasks.shadowJar)
    dependsOn(project(":common").tasks.shadowJar)
    from(zipTree(project(":common").tasks.shadowJar.get().archiveFile))
    configurations = listOf(shadowCommon)
    archiveClassifier.set("dev-shadow")
}

tasks.named<RemapJarTask>("remapJar") {
    dependsOn(tasks.shadowJar.get())
    asJar {
        inputFile.set(tasks.shadowJar.get().archiveFile)
        archiveClassifier.set(project.name)
    }
}

tasks.jar {
    archiveClassifier.set("dev")
}

tasks.sourcesJar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val commonSources = project(":common").tasks.sourcesJar
    dependsOn(commonSources.get())
    from(commonSources.get().archiveFile.map { zipTree(it) })
}