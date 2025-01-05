import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.withType
import org.jmailen.gradle.kotlinter.tasks.InstallPreCommitHookTask
import org.jmailen.gradle.kotlinter.tasks.InstallPrePushHookTask

class KotlinJvmConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply("org.jetbrains.kotlin.jvm")
        project.plugins.apply("org.jmailen.kotlinter")

        project.tasks.withType<Test>().configureEach {
            useJUnitPlatform()
            testLogging {
                events(
                    TestLogEvent.FAILED,
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED
                )
            }
        }

        project.afterEvaluate {
            project.tasks.named("build").configure {
                if (!project.rootProject.extra.has("internal.install-git-hooks") &&
                    !project.hasProperty("skipGitHooks")
                ) {
                    project.rootProject.extra["internal.install-git-hooks"] = true

                    val preCommit: TaskProvider<InstallPreCommitHookTask> =
                        project.rootProject.tasks.register(
                            "installPreCommitHook",
                            InstallPreCommitHookTask::class.java
                        ) {
                            group = "build setup"
                            description = "Installs Kotlinter Git pre-commit hook"
                        }

                    val prePush: TaskProvider<InstallPrePushHookTask> =
                        project.rootProject.tasks.register(
                            "installPrePushHook",
                            InstallPrePushHookTask::class.java
                        ) {
                            group = "build setup"
                            description = "Installs Kotlinter Git pre-push hook"
                        }

                    dependsOn(preCommit, prePush)
                }
            }
        }
    }
}
