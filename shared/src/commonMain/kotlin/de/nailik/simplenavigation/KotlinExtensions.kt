package de.nailik.simplenavigation

fun <T> List<T>.takeWhileInclusive(predicate: (T) -> Boolean) = buildList {
    with(this@takeWhileInclusive.iterator()) {
        while (hasNext()) {
            val next = next()
            add(next)
            if (!predicate(next)) break
        }
    }
}