package ch.dreipol.kmpexample.android.ui

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ch.dreipol.kmpexample.android.MyApplicationTheme
import ch.dreipol.kmpexample.android.ui.utils.subscribeAsState
import ch.dreipol.kmpexample.redux.ApplicationStore
import ch.dreipol.kmpexample.redux.Screen
import ch.dreipol.kmpexample.redux.actions.NavigationAction
import ch.dreipol.kmpexample.redux.createPreviewStore
import ch.dreipol.kmpexample.redux.screenName
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlin.reflect.KClass

private const val navAnimationDuration = 500

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainPage(navHostController: NavHostController, store: ApplicationStore) {
    AnimatedNavHost(
        navHostController,
        startDestination = screenName(Screen.ChatScreen::class),
    ) {
        backHandlingComposable(
            Screen.ChatScreen::class,
            store = store,
        ) {
            ChatContainer(store)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.backHandlingComposable(
    screen: KClass<out Screen>,
    store: ApplicationStore,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(navAnimationDuration))
    },
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(navAnimationDuration))
    },
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) = composable(screenName(screen), arguments, deepLinks, enterTransition, exitTransition, popEnterTransition, popExitTransition) {
    Scaffold { padding ->
        val navigationState = subscribeAsState(
            store = store,
            selector = { state -> state.navigationState },
        )
        val handleBack = navigationState.value.screens.size > 1
        BackHandler(handleBack) {
            store.dispatch(NavigationAction.Back)
        }
        Box(modifier = Modifier.padding(padding)) {
            content(it)
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainPagePreview() {
    MyApplicationTheme {
        MainPage(NavHostController(LocalContext.current), createPreviewStore())
    }
}