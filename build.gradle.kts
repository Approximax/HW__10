plugins {
    id 'java'
}



repositories {
    mavenCentral()
}

compileJava {
    options.encoding = 'UTF-8'
}
compileTestJava {
    options.encoding = 'UTF-8'
}

dependencies {
    testImplementation(
            'org.junit.jupiter:junit-jupiter:5.10.1',
            'com.codeborne:selenide:7.0.4',
    )
}

test {
    useJUnitPlatform()
}

