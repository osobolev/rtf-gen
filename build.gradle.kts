description = "RTF generation library"

plugins {
    `module-lib`
}

group = "io.github.osobolev"
version = "1.0.3"

dependencies {
    testImplementation("junit:junit:4.13.2")
}

(publishing.publications["mavenJava"] as MavenPublication).pom {
    name.set("${project.group}:${project.name}")
    description.set("RTF generation library derived from iText-2.1.7. All PDF features and RTF parsing are removed. Code is modernized for Java 8.")
    url.set("https://github.com/osobolev/rtf-gen")
    licenses {
        license {
            name.set("GNU General Lesser Public License (LGPL) version 3.0")
            url.set("http://www.gnu.org/licenses/lgpl.html")
            distribution.set("repo")
        }
        license {
            name.set("Mozilla Public License Version 2.0")
            url.set("http://www.mozilla.org/MPL/2.0/")
            distribution.set("repo")
        }
    }
    developers {
        developer {
            name.set("Oleg Sobolev")
            organizationUrl.set("https://github.com/osobolev")
        }
    }
    scm {
        connection.set("scm:git:https://github.com/osobolev/rtf-gen.git")
        developerConnection.set("scm:git:https://github.com/osobolev/rtf-gen.git")
        url.set("https://github.com/osobolev/rtf-gen")
    }
}
