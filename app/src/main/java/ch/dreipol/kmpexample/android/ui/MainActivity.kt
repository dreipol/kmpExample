package ch.dreipol.kmpexample.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.Navigator
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.subscribeNavigationState
import ch.dreipol.kmpexample.android.MyApplicationTheme
import ch.dreipol.kmpexample.getAppConfiguration
import ch.dreipol.kmpexample.redux.Screen
import ch.dreipol.kmpexample.redux.actions.NavigationAction
import ch.dreipol.kmpexample.redux.screenName
import ch.dreipol.kmpexample.redux.state.ApplicationState
import ch.dreipol.kmpexample.redux.state.NavigationState
import co.touchlab.kermit.Logger
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber

class MainActivity : ComponentActivity(), Navigator<ApplicationState, NavigationState> {

    override val store: Store<ApplicationState>
        get() = getAppConfiguration().store
    private lateinit var cancelNavigationStateSubscription: StoreSubscriber

    private lateinit var onUpdateNavigationState: (NavigationState) -> Unit

    override fun getNavigationState(): NavigationState = store.state.navigationState

    override fun updateNavigationState(navigationState: NavigationState) {
        if (navigationState.screens.isEmpty()) {
            return
        }
        if (this::onUpdateNavigationState.isInitialized) {
            onUpdateNavigationState(navigationState)
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cancelNavigationStateSubscription = subscribeNavigationState()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()

                    onUpdateNavigationState = {
                        val backStack =
                            navController.currentBackStack.value.filterNot { backStackEntry -> backStackEntry.destination.route == null }
                        val routes = backStack.map { it.destination.route }.joinToString()
                        Logger.d { routes }
                        print(backStack)
                        val expectedRoute = screenName(it.screens.last()::class)
                        val currentDestination = navController.currentDestination
                        if (currentDestination != null && currentDestination.route != expectedRoute) {
                            val popTo = findFirstMatchingBackStackScreen(it.screens, backStack)
                            if (backStack.size >= it.screens.size && popTo != null && popTo.screenName() == expectedRoute) {
                                navController.popBackStack(route = popTo.screenName(), inclusive = false)
                            } else {
                                navController.navigate(expectedRoute) { buildNavOptions(it, backStack) }
                            }
                        }
                    }
                    LaunchedEffect(Unit) {
                        // initial sync of navController with current navigation state
                        onUpdateNavigationState(store.state.navigationState)
                        navController.addOnDestinationChangedListener { controller, destination, _ ->
                            val backStack =
                                controller.currentBackStack.value.filterNot { backStackEntry -> backStackEntry.destination.route == null }
                            val screens = store.state.navigationState.screens
                            if (backStack.size == screens.size - 1 && destination.route == screens.dropLast(1).lastOrNull()?.screenName()) {
                                // hardware back detected
                                Logger.d { "hardware back" }
                                store.dispatch(NavigationAction.Back)
                            }
                        }
                    }

                    MainPage(
                        navHostController = navController,
                        store = store,
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelNavigationStateSubscription()
    }

    private fun findFirstMatchingBackStackScreen(screens: List<Screen>, backStack: List<NavBackStackEntry>): Screen? {
        return screens.reversed().firstOrNull { screen -> backStack.find { it.destination.route == screen.screenName() } != null }
    }

    private fun NavOptionsBuilder.buildNavOptions(
        navigationState: NavigationState,
        backStack: List<NavBackStackEntry>,
    ) {
        if (backStack.drop(1).size >= navigationState.screens.size) {
            backStack[1].destination.route?.let {
                popUpTo(it) {
                    inclusive = true
                }
            }
        }
    }
}
