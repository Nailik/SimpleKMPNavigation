package de.nailik.simplenavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.staticCompositionLocalOf
import de.nailik.simplenavigation.item.Item

public val LocalNavigator: ProvidableCompositionLocal<Navigator?> =
    staticCompositionLocalOf { null }

@Composable
public fun NavigationContent(
    vararg screens: Item<*>,
) {

    val navigator = rememberSaveable { NavigatorImpl(*screens) }

    val state by navigator.visibleStack.collectAsState()

    state.forEach {
        it.Content()
    }

}