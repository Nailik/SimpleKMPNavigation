package de.nailik.simplenavigation

import de.nailik.simplenavigation.item.Item
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KClass

interface Navigator {

    /**
     * contains visible elements until one element that blocks the visibility of the next element
     * same element can be contained multiple times
     */
    val visibleStack: StateFlow<List<Item<*>>>

    /**
     * push one item onto the stack
     */
    fun <T> push(item: Item<T>)

    /**
     * push a list of items onto the stack
     */
    fun <T> push(items: List<Item<T>>)

    /**
     * pop last item from Stack
     * calls resultCallback with result stored in item
     */
    fun pop()

    /**
     * pop last item of this type Stack
     * calls resultCallback with result stored in item
     */
    fun <T> pop(clazz: KClass<out Item<T>>)

    /**
     * pop all items until last item (inclusive by flag) of this type from Stack
     * calls resultCallback with result stored in item for all removed items
     */
    fun <T> popUntil(
        clazz: KClass<out Item<T>>,
        inclusive: Boolean = false
    )

    /**
     * pop last item of this type Stack
     * sets result of the Item
     * calls resultCallback with result stored in item
     */
    fun <T> Item<T>.pop(result: T? = null)

    /**
     * pop all items until last item (inclusive by flag) of this type from Stack
     * sets result of the Item
     * calls resultCallback with result stored in item for all removed items
     */
    fun <T> Item<T>.popUntil(
        result: T? = null,
        inclusive: Boolean = false
    )

}