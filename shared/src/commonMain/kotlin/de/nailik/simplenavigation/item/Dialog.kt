package de.nailik.simplenavigation.item

abstract class Dialog<T>(
    resultCallback: ((T?) -> Unit) = {}
) : Item<T>(
    resultCallback = resultCallback
) {

    override val nextItemVisible: Boolean = true


}