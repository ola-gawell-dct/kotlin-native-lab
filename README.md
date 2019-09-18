## Preparations

1. Install Java SDK
1. Install IntelliJ IDEA 2019.2
1. Install Android SDK
1. Install latest Xcode (if you want to test the app on iOS)
1. Install Gradle CLI

## Step 1 - Create Project

### Create the Project

1. Open IntelliJ IDEA
2. Choose "Create new Project"
3. Under "Kotlin" select "Mobile Android/iOS"
4. Select JVM dir as Gradle JVM
5. Choose a name for you project (I will choose kotlin-native-lab)
6. Create the project

You will get this error message on the first build:

```
The SDK directory '~/workshops/kotlin/kotlin-native-lab/PleaseSpecifyAndroidSdkPathHere' does not exist.

Please fix the 'sdk.dir' property in the local.properties file.
```

Locate the `local.properties` file and change `sdk.dir` to the path to your Android SDK

### Generate gradlew file

1. Open the file `gradle/wrapper/gralde-wrapper.properties`
2. Check that `distributionUrl` is pointning to version `5.3.1` of gradle. If not, change the line to:

```
distributionUrl=https\://services.gradle.org/distributions/gradle-5.3.1-all.zip
```

3. Open the terminal in the project root. Execute the command:

```
gralde wrapper
```

The command will generate a gradlew file that will be used when building the project.

### Set correct versions

It is crucial to use specific versions of different libaries and tools. Some libs only supports some vesions of kotlin etc., so be very careful when updating a lib to the latest version.

Update the file gradle.properties with this content:

```
kotlin.code.style=official

# versions
kotlin_version = 1.3.40
kotlin_native_version = 1.3.40
ktor_version = 1.2.2
kotlinx_coroutines_version = 1.2.2
kotlinx_serialization_version = 0.11.1
kodein_version = 6.3.0

# android
gradle_android_version = 3.2.0
recycleview_version = 1.0.0
appcompat_version = 1.0.0
constraint_layout_version = 2.0.0-beta2
android.useAndroidX=true
android.enableJetifier=true
```

When changing versions fo libs I often ran into problems with cached versions of the lib still remaining in the build. The only way I found to remove them from the cache was to select `File > Invalidate caches / Restart ...` and the select `Invalidate and Restart`.

1. Update `build.gradle` in the projet root to use the `gradle_android_version`. Notice that we need double quotes when using parameters in the string.

```
classpath "com.android.tools.build:gradle:$gradle_android_version"
```

2. Add the following line belove the line from the previous step:

```
classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
```

3. And you might need to add this to repositories

```ruby
mavenCentral()
```

3. Open `app/src/main/java/Sample/SampleAndroid.kt` and change

```
import android.support.v7.app.AppCompatActivity
```

to

```
import androidx.appcompat.app.AppCompatActivity
```

4. Replace the content of `app/build.gradle` with this:

```ruby
plugins {
    id 'kotlin-multiplatform'
}
repositories {
    google()
    mavenCentral()
    jcenter()
}
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-kapt'

android {
    compileSdkVersion 28
    defaultConfig {
        applicationId 'com.example.kotlinnativelab'
        minSdkVersion 15
        targetSdkVersion 28
        versionCode 1
        versionName '1.0'
        testInstrumentationRunner 'android.support.test.runner.AndroidJUnitRunner'
    }
    buildTypes {
        release {
            minifyEnabled false
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
}

kotlin {
    targets {
        fromPreset(presets.android, 'android')
        fromPreset(presets.iosX64, 'ios') { //iosX64 is just for the iOS Simulator
            binaries {
                framework {
                    baseName = "app"
                }
            }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation kotlin('stdlib-common')
            }
        }
        commonTest {
            dependencies {
        		implementation kotlin('test-common')
        		implementation kotlin('test-annotations-common')
            }
        }
        androidMain {
            dependencies {
                implementation kotlin('stdlib')
            }
        }
        androidTest {
            dependencies {
                implementation kotlin('test')
                implementation kotlin('test-junit')
            }
        }
        iosMain {
        }
        iosTest {
        }
    }
}

// This task attaches native framework built from ios module to Xcode project
// (see iosApp directory). Don't run this task directly,
// Xcode runs this task itself during its build process.
// Before opening the project from iosApp directory in Xcode,
// make sure all Gradle infrastructure exists (gradle.wrapper, gradlew).
task copyFramework {
    def buildType = project.findProperty('kotlin.build.type') ?: 'DEBUG'
    def target = project.findProperty('kotlin.target') ?: 'ios'
    dependsOn kotlin.targets."$target".binaries.getFramework(buildType).linkTask

    doLast {
        def srcFile = kotlin.targets."$target".binaries.getFramework(buildType).outputFile
        def targetDir = getProperty('configuration.build.dir')
        copy {
            from srcFile.parent
            into targetDir
            include 'app.framework/**'
            include 'app.framework.dSYM'
        }
    }
}
```

5. Change `applicationId 'com.example.kotlinnativelab'` to some other Android application id if you want.

6. Replace the content of `app/main/res/layout/activity_main.xml` with this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/main_text"
        android:textSize="42sp"
        android:layout_margin="5sp"
        android:textAlignment="center"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

7. Build the project by pressing the hammer in the toolbar. If there is any error during the build it is easier to get the log by building from the command line. Point your terminal to the project root and execute the command

```
./gradlew build
```

#### Run on Android

If the build was successfull the name `app` with an Android symbol infront should be displayed besides the hammer . Press the Play button to the right of `app` to start the Android app. If you see the string "Hello from Android" on the Android emulator everything worked as expected.

#### Run on iOS

Open the XCode project found at `<projectRoot>/iosApp/iosApp.xcodeproj`. Press the play button. If you see the string "Hello from iOS" everythin worked as expected.

### Conclusion

Look around in the project and see if you can figure out why the Android and iOS apps show different strings. Read more about it here [Platform-Specific Declarations](https://kotlinlang.org/docs/reference/platform-specific-declarations.html). Try some small changes and see how they effect the different platforms.

## Step 2 - HTTP Calls

Add this to the `build.gradle` in app and sync.

```
commonMain {
    dependencies {
        ...
        implementation "io.ktor:ktor-client-core:$ktor_version"
        implementation "io.ktor:ktor-client-json:$ktor_version"
        implementation "io.ktor:ktor-client-serialization:$ktor_version"
        ...
    }
}
androidMain {
    dependencies {
        ...
        implementation "io.ktor:ktor-client-core-jvm:$ktor_version"
        implementation "io.ktor:ktor-client-json-jvm:$ktor_version"
        implementation "io.ktor:ktor-client-serialization-jvm:$ktor_version"
        implementation "io.ktor:ktor-client-android:$ktor_version"
        ...
    }
}
iosMain {
    dependencies {
        ...
        implementation "io.ktor:ktor-client-ios-iosx64:$ktor_version"
        implementation "io.ktor:ktor-client-ios:$ktor_version"
        implementation "io.ktor:ktor-client-core-native:$ktor_version"
        implementation "io.ktor:ktor-client-json-native:$ktor_version"
        implementation "io.ktor:ktor-client-serialization-iosx64:$ktor_version"
        ...
    }
}
```

Let's create a API class that uses ktor. In `commonMain` add this class.

```kotlin
package sample

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get

interface API {
    suspend fun getUsers(): String
}

class KtorAPI(
    private val baseUrl: String = "https://reqres.in/api/"
): API {

    private val client = HttpClient()

    private fun buildUrl(path: String): String {
        return "${baseUrl}${path}"
    }

    override suspend fun getUsers(): String {
        return client.get(buildUrl("users"))
    }
}
```

Try to use it in the SampleAndroid class. You will notice that `client.get()` is a suspend function so we need to call from inside a coroutine scope.

### Add Simple Coroutines

Kotlin/Native does not have full coroutines support. You can only use coroutines on the main thread. But right now that's all we need. We only want to post the result from the get request on the main thread.

Add the following in app's `build.gradle`

```ruby
commonMain {
    dependencies {
        ...
        implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$kotlinx_coroutines_version"
        ...
    }
}
androidMain {
    dependencies {
        ...
        implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinx_coroutines_version"
        ...
    }
}
iosMain {
    dependencies {
        ...
        implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$kotlinx_coroutines_version"
        ...
    }
}
```

Create a simple interactor that creates a coroutines scope and calls the API function. It could look something like this:

```kotlin
package sample

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class GetUsersUseCase(
    val api: API,
    var context: CoroutineContext = MainDispatcher
) {

    private var job: Job = Job()

    fun execute(
        onComplete: (String) -> Unit,
        onError: ((Throwable) -> Unit)? = null,
        onCancel: ((CancellationException) -> Unit)? = null
    ) {
        job = Job()
        CoroutineScope(context + job).launch {
            try {
                val result = api.getPosts()
                onComplete(result)
            } catch (cancellationException: CancellationException) {
                onCancel?.invoke(cancellationException)
            } catch (e: Exception) {
                onError?.invoke(e)
            }
        }
    }
}
```

You need to specify what `MainDispatcher` is on both platforms. Use `actual` and `expect` to do that. The implementation for iOS is a bit tricky so you can copy this:

```kotlin
internal actual val MainDispatcher: CoroutineDispatcher = NsQueueDispatcher(dispatch_get_main_queue())

internal class NsQueueDispatcher(
    private val dispatchQueue: dispatch_queue_t
) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatchQueue) {
            block.run()
        }
    }
}
```

Try to use the use case from the Android Sample App (and iOS if you want).

### Parse JSON

Add the following dependencies to app's `build.gradle`

```ruby
commonMain {
    dependencies {
        ...
        implementation "io.ktor:ktor-client-serialization:$ktor_version"
        implementation "org.jetbrains.kotlinx:kotlinx-serialization-runtime:$kotlinx_serialization_version"
        ...
    }
}
androidMain {
    dependencies {
        implementation "org.jetbrains.kotlinx:kotlinx-serialization-runtime:$kotlinx_serialization_version"
        implementation "io.ktor:ktor-client-serialization-jvm:$ktor_version"
    }
}
iosMain {
    dependencies {
        implementation "org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$kotlinx_serialization_version"
        implementation "io.ktor:ktor-client-serialization-iosx64:$ktor_version"
        implementation "io.ktor:ktor-client-serialization-iosarm64:$ktor_version"
    }
}
```

Create data classes for the response
```kotlin
@Serializable
data class User(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)

@Serializable
data class GetUsersResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>
)
```

You might need to add this dependency manually to the API class
```
import kotlinx.serialization.Serializable
```

Change the initialization of the (ktor)client to this:

```kotlin
private val client = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(Json.nonstrict).apply {
            setMapper(GetUsersResponse::class, GetUsersResponse.serializer())
        }
    }
}
```

Change the `API` and `GetUsersUseCase` to return `GetUsersResponse` instead of `String`. 





## Step 4 - Async code

Start by adding the following line to the commonMain target in the app's build.gradle file:

```implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$kotlinx_coroutines_version"```

and the following to androidMain

```implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinx_coroutines_version"```

and last to iosMain

```implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$kotlinx_coroutines_version"```

Add this to ```android { ... }``` after ```buildTypes {...}```

```
packagingOptions {
   exclude 'META-INF/*.kotlin_module'
}
```

To make coroutines work for iOS we need to add this line to the root ```settings.gradle```

```
enableFeaturePreview('GRADLE_METADATA')
```


