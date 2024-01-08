package de.nailik.simplenavigation.item

import androidx.compose.runtime.Composable

abstract class Item<T>(val resultCallback: ((T?) -> Unit) = {}) {

    var result: T? = null

    abstract val nextItemVisible: Boolean

    @Composable
    abstract fun Content()

    internal fun removed() {
        resultCallback(result)
    }

}