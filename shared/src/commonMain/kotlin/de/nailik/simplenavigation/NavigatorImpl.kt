package de.nailik.simplenavigation

import de.nailik.simplenavigation.item.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

class NavigatorImpl(
    vararg initialItems: Item<*>
) : Navigator {

    private val screenStack = MutableStateFlow(initialItems.toMutableList())
    override val visibleStack: StateFlow<List<Item<*>>>
        get() = screenStack.map { list ->
            list.takeWhileInclusive { !it.nextItemVisible }
        }.stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.Eagerly, emptyList())

    override fun <T> push(item: Item<T>) {
        screenStack.update {
            it.also { it.add(item) }
        }
    }

    override fun <T> push(items: List<Item<T>>) {
        screenStack.update {
            it.also { it.addAll(items) }
        }
    }

    override fun pop() {
        screenStack.update {
            it.also { it.removeLast().also { item -> item.removed() } }
        }
    }

    override fun <T> pop(clazz: KClass<out Item<T>>) {
        screenStack.update {
            it.also {
                val index = it.indexOfLast { item -> item::class == clazz }
                if (index in it.indices) {
                    it.removeAt(index).removed()
                }
            }
        }
    }

    override fun <T> popUntil(
        clazz: KClass<out Item<T>>,
        inclusive: Boolean
    ) {
        screenStack.update {
            it.also {
                val index = it.indexOfLast { item -> item::class == clazz }
                if (index in it.indices) {
                    it.subList(0, index).forEach { item -> item.removed() }
                    if (inclusive) {
                        it.removeLast().removed()
                    }
                }
            }
        }
    }

    override fun <T> Item<T>.pop(result: T?) {
        this.result = result
        this@NavigatorImpl.pop(this::class)
    }

    override fun <T> Item<T>.popUntil(
        result: T?,
        inclusive: Boolean
    ) {
        this.result = result
        this@NavigatorImpl.popUntil(this::class, inclusive)
    }

}