import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SourcesJar

description = "RTF generation library"

plugins {
    id("com.vanniktech.maven.publish") version "0.37.0"
    id("module-lib")
}

group = "io.github.osobolev"
version = "1.0.7"

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates("${project.group}", "${project.name}", "${project.version}")
    configure(JavaLibrary(
        javadocJar = JavadocJar.Javadoc(),
        sourcesJar = SourcesJar.Sources()
    ))
}

mavenPublishing.pom {
    name = "${project.group}:${project.name}"
    description = "RTF generation library derived from iText-2.1.7. All PDF features and RTF parsing are removed. Code is modernized for Java 8."
    url = "https://github.com/osobolev/rtf-gen"
    licenses {
        license {
            name = "GNU General Lesser Public License (LGPL) version 3.0"
            url = "http://www.gnu.org/licenses/lgpl.html"
            distribution = "repo"
        }
        license {
            name = "Mozilla Public License Version 2.0"
            url = "http://www.mozilla.org/MPL/2.0/"
            distribution = "repo"
        }
    }
    developers {
        developer {
            name = "Oleg Sobolev"
            organizationUrl = "https://github.com/osobolev"
        }
    }
    scm {
        connection = "scm:git:https://github.com/osobolev/rtf-gen.git"
        developerConnection = "scm:git:https://github.com/osobolev/rtf-gen.git"
        url = "https://github.com/osobolev/rtf-gen"
    }
}

tasks.withType(com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask::class).configureEach {
    rejectVersionIf {
        candidate.version.contains("-M") ||
        candidate.version.contains("-RC") ||
        candidate.version.contains("-rc")
    }
}
