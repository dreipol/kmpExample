package ch.dreipol.kmpexample.redux

import kotlin.reflect.KClass

fun Screen.screenName(): String = screenName(this::class)
fun screenName(clazz: KClass<*>): String = clazz.qualifiedName ?: throw IllegalStateException()

sealed class Screen {
    data object Chat : Screen()
    data object Profile : Screen()
}