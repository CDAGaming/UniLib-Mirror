import xyz.wagyourtail.unimined.api.minecraft.patch.fabric.FabricLikePatcher
import java.util.regex.Pattern

plugins {
    id("com.hypherionmc.modutils.modpublisher") version "2.1.5"
}

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
val isNeoForge: Boolean by extra
val accessWidenerFile: File by extra
val isMCPJar: Boolean by extra
val isModern: Boolean by extra
val fmlName: String by extra
val versionFormat: String by extra
val versionLabel: String by extra
val mcVersionLabel: String by extra
val fileFormat: String by extra

unimined.minecraft {
    defaultRemapJar = false
    if (!isJarMod) {
        val fabricData: FabricLikePatcher.() -> Unit = {
            if (accessWidenerFile.exists()) {
                accessWidener(accessWidenerFile)
            }
            loader("fabric_loader_version"()!!)
            customIntermediaries = true
        }
        if (isModern) {
            fabric(fabricData)
        } else {
            legacyFabric(fabricData)
        }
    }
}

val shadeOnly: Configuration by configurations.creating
val shade: Configuration by configurations.creating
val runtime: Configuration by configurations.creating

configurations.implementation.get().extendsFrom(shade)
configurations.runtimeOnly.get().extendsFrom()

dependencies {
    // Legacy Dependencies, based on Protocol Version
    if (isLegacy) {
        if (protocol <= 61) { // MC 1.5.2 and below
            shade("com.google.code.gson:gson:2.2.2")
        }
    }

    // CORE APIs
    shade("io.github.CDAGaming:unicore:${"core_version"()!!}") {
        isTransitive = false
    }

    // LeniReflect
    shade("net.lenni0451:Reflect:${"reflect_version"()!!}")

    // SLF4J Dependencies (If below 1.17)
    if (isLegacy || protocol < 755) {
        shade("org.slf4j:slf4j-api:1.7.36")
        if (isLegacy) {
            shade("org.slf4j:slf4j-jdk14:1.7.36")
        } else {
            runtime("org.slf4j:slf4j-jdk14:1.7.36")

            // 17w15a (1.12) and higher use 2.x's full releases of Log4j
            // while anything below that uses (Or should be using) the *fixed* version of 2.0-beta9
            val log4jVersion = if (protocol >= 321) "2.0" else "2.0-beta9"
            shadeOnly("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion") {
                exclude(group = "org.apache.logging.log4j", module = "log4j-api")
                exclude(group = "org.apache.logging.log4j", module = "log4j-core")
            }
        }
    }
}

// JSON to LANG Conversion Setup (Below 18w02a, 1.13)
val mainResources = "$projectDir/src/main/resources"
val generatedResources = "${layout.buildDirectory.asFile.get()}/generated-resources"

if (isLegacy || protocol < 353) {
    sourceSets {
        main {
            output.dir(mapOf("builtBy" to "generateMyResources"), generatedResources)
        }
    }
}

tasks.jar {
    from(sourceSets.main.get().output)
}

tasks.shadowJar {
    configurations = listOf(project.configurations.getByName("shade"), project.configurations.getByName("shadeOnly"))

    // Meta Exclusions
    exclude("**/DEPENDENCIES*")
    exclude("**/LICENSE*")
    exclude("**/Log4J*")
    exclude("META-INF/NOTICE*")
    exclude("META-INF/versions/**")

    // Package Relocations
    relocate("net.lenni0451", "external.net.lenni0451")
    relocate("io.github.classgraph", "external.io.github.classgraph")
    relocate("nonapi.io.github.classgraph", "external.nonapi.io.github.classgraph")
    if (protocol < 755) {
        relocate("org.slf4j", "external.org.slf4j")
        relocate("org.apache.logging.slf4j", "external.org.apache.logging.slf4j")
    }

    archiveClassifier.set("shadow-dev")
}

tasks.processResources {
    filesMatching("assets/$modId/lang/**") {
        val text = file.readText(Charsets.UTF_8)
        if (text.isEmpty() || text == "{}") {
            exclude()
        }
    }
}
tasks.processResources.get().outputs.upToDateWhen { false }

tasks.register("generateMyResources") {
    doFirst {
        val langDir = File(mainResources, "/assets/$modId/lang")
        val resultDir = File(generatedResources, "/assets/$modId/lang")
        langDir.mkdirs()
        resultDir.mkdirs()
        langDir.walkTopDown().forEach { file ->
            if (file.isFile && file.path.endsWith(".json")) {
                var currentString: String
                var contents = "#PARSE_ESCAPES\n"
                var replacements = 0
                println("Converting json to lang: ${file.path}")
                // Logic from TranslationUtils#getTranslationMap
                // Does not include the escape replacements, as those are done later
                file.readLines(Charsets.UTF_8).forEach { line ->
                    currentString = line.trim()
                    if (!currentString.startsWith("#") && !currentString.startsWith("[{}]") && currentString.contains(":")) {
                        val splitTranslation = currentString.split(":", limit = 2)
                        val str1 = splitTranslation[0].substring(1, splitTranslation[0].length - 1).trim()
                        val str2 = splitTranslation[1].substring(
                            2,
                            splitTranslation[1].length - if (splitTranslation[1].endsWith(",")) 2 else 1
                        ).trim()
                        contents += "$str1=$str2\n"
                        replacements++
                    }
                }
                // Only proceed if we actually performed any replacements
                if (replacements > 0) {
                    var resultName = file.name.replace(".json", ".lang")
                    if (protocol <= 210) {
                        // On 1.10.2 (Pack Format 2 or below) or Legacy MC
                        // Adjust name format from xx_xx to xx_XX
                        val matches = Pattern.compile("_.+?\\.").matcher(resultName)
                        while (matches.find()) {
                            val match = matches.group()
                            resultName = resultName.replace(match, match.uppercase())
                        }
                    }
                    val resultFile = File(resultDir, resultName)
                    resultFile.createNewFile()
                    println("Outputting to: ${resultFile.path}")
                    resultFile.writeText(contents.replace(Regex("(?s)\\\\(.)"), "$1"), Charsets.UTF_8)
                } else {
                    println("Skipping ${file.path} (No content found)")
                }
            }
        }
    }
}

// Setup Data for Uploading
var targetFile = file("$rootDir/build/libs/$fileFormat.jar")
if (!targetFile.exists() && (isJarMod)) {
    // Fallback to an alternative Sub-Project Output when in a Jar Mod configuration and the target file isn't there
    targetFile = file("$rootDir/$fmlName/build/libs/$fileFormat-$fmlName.jar")
}

// Setup Game Versions to upload for
val uploadVersions = mutableListOf("mc_version"()!!)
for (v in "additional_mc_versions"()!!.split(",")) {
    if (v.isNotEmpty()) {
        uploadVersions.add(v)
    }
}

// Setup Game Loaders to upload for
var uploadLoaders = "enabled_platforms"()!!.split(",").toMutableList()
for (v in "additional_loaders"()!!.split(",")) {
    if (v.isNotEmpty()) {
        uploadLoaders.add(v)
    }
}

// Ensure Forge // NeoForge Upload Compatibility, when specified
if (isNeoForge) {
    uploadLoaders = uploadLoaders.map { if (it == "forge") "neoforge" else it }.toMutableList()
}

publisher {
    apiKeys {
        curseforge(System.getenv("CF_APIKEY"))
        modrinth(System.getenv("MODRINTH_TOKEN"))
        nightbloom(System.getenv("NIGHTBLOOM_TOKEN"))
    }

    debug = false
    curseID = "1056812"
    modrinthID = "nT86WUER"
    nightbloomID = modId
    versionType = "deploymentType"()!!.lowercase()
    changelog = file("$rootDir/Changes.md").readText()
    projectVersion = versionFormat.replace(Regex("\\s"), "").lowercase() // Modrinth Only
    displayName =
        "$modName v${"versionId"()}${if (versionLabel.isEmpty()) "" else " $versionLabel"} ($mcVersionLabel)"
    gameVersions = uploadVersions
    loaders = uploadLoaders
    curseEnvironment = "client"
    artifact = targetFile
}
