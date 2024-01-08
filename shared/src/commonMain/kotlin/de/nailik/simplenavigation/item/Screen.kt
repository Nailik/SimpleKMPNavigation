package de.nailik.simplenavigation.item

abstract class Screen<T>(
    resultCallback: ((T?) -> Unit) = {}
) : Item<T>(
    resultCallback = resultCallback
) {

    override val nextItemVisible: Boolean = false

}