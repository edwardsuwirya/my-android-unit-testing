plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("jacoco")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.3"
    buildFeatures {
        viewBinding = true
    }
    jacoco {
        buildToolsVersion = "0.8.6"
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }
    defaultConfig {
        applicationId = "com.enigmacamp.myunittesting"
        minSdkVersion(26)
        targetSdkVersion(29)
        versionCode(1)
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val room_version = "2.2.5"
val kotlin_coroutine_version = "1.4.1"
val lifecycle_ktx_version = "2.2.0"
val kotlin_version = "1.4.2"

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.4.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlin_coroutine_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_ktx_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_ktx_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_ktx_version")

    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("org.mockito:mockito-core:3.9.0")
    testImplementation("org.mockito:mockito-inline:3.9.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:3.7.0")
    androidTestImplementation("com.squareup.okhttp3:okhttp:3.14.9")
    debugImplementation("androidx.fragment:fragment-testing:1.3.6")
}

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

val fileFilter = mutableSetOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "android/**/*.*",
    "**/*\$Lambda$*.*",
    "**/*\$inlined$*.*"
)

val classDirectoriesTree = fileTree(project.buildDir) {
    include(
        "**/classes/**/main/**",
        "**/intermediates/classes/debug/**",
        "**/intermediates/javac/debug/*/classes/**",
        "**/tmp/kotlin-classes/debug/**"
    )

    exclude(fileFilter)
}

val sourceDirectoriesTree = fileTree(project.buildDir) {
    include(
        "src/main/java/**",
        "src/main/kotlin/**",
        "src/debug/java/**",
        "src/debug/kotlin/**"
    )
}

val executionDataTree = fileTree(project.buildDir) {
    include(
        "outputs/code_coverage/**/*.ec",
        "jacoco/jacocoTestReportDebug.exec",
        "jacoco/testDebugUnitTest.exec",
        "jacoco/test.exec"
    )
}

fun JacocoReportsContainer.reports() {
    xml.isEnabled = true
    html.isEnabled = true
    xml.destination = file("${buildDir}/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
    html.destination = file("${buildDir}/reports/jacoco/jacocoTestReport/html")
}

fun JacocoReport.setDirectories() {
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}


if (tasks.findByName("jacocoAndroidTestReport") == null) {

    tasks.register<JacocoReport>("jacocoAndroidTestReport") {
        description = "Code coverage report for both Android and Unit tests."
        dependsOn("testDebugUnitTest", "createDebugCoverageReport")
        reports {
            reports()
        }
        setDirectories()
    }
}
