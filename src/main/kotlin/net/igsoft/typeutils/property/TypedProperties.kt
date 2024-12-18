package net.igsoft.typeutils.property

import net.igsoft.typeutils.marker.Marker
import net.igsoft.typeutils.marker.TypedMarker

@Suppress("UNCHECKED_CAST", "unused")
class TypedProperties(
    private val map: MutableMap<Marker, Any?> = mutableMapOf(),
    configBlock: TypedProperties.() -> Unit = {}
) : MutableTypedProperties {

    init {
        configBlock()
    }

    override operator fun <T> set(key: TypedMarker<T>, value: T) {
        map[key] = value
    }

    override fun get(key: Marker): Any? = map[key]

    override fun <T> get(key: TypedMarker<T>): T? = get(key as Marker) as? T

    override fun getValue(key: Marker): Any {
        val value = get(key)

        if (isPropertyKeyMissing(value, key)) {
            throw NoSuchElementException("Marker $key is missing in the properties")
        }

        return value!!
    }

    override fun <T> getValue(key: TypedMarker<T>): T {
        val value = get(key)

        if (isPropertyKeyMissing(value, key)) {
            throw NoSuchElementException("Marker $key is missing in the properties")
        }

        return value as T
    }

    override fun <T> getOrDefault(key: TypedMarker<T>, defaultValue: T): T {
        val value = get(key)

        if (isPropertyKeyMissing(value, key)) {
            return defaultValue
        }

        return value as T
    }

    override fun <T> getOrElse(key: TypedMarker<T>, calculateValue: () -> T): T {
        val value = get(key)

        if (isPropertyKeyMissing(value, key)) {
            return calculateValue()
        }

        return value as T
    }

    override fun <T> getOrPut(key: TypedMarker<T>, calculateValue: () -> T): T {
        var value = get(key)

        if (isPropertyKeyMissing(value, key)) {
            value = calculateValue()
            set(key, value)
        }

        return value as T
    }

    override val size: Int get() = map.size

    override val entries: MutableSet<MutableMap.MutableEntry<Marker, Any?>> get() = map.entries

    override val keys: MutableSet<Marker> get() = map.keys

    override val values: MutableCollection<Any?> get() = map.values

    override fun isEmpty(): Boolean = map.isEmpty()

    override fun containsValue(value: Any?): Boolean = map.containsValue(value)

    override fun containsKey(key: Marker): Boolean = map.containsKey(key)

    override fun clear(): Unit = map.clear()

    override fun <T> put(key: TypedMarker<T>, value: T): T = map.put(key, value) as T

    override fun putAll(from: ImmutableTypedProperties) = map.putAll(from)

    override fun <T> remove(key: TypedMarker<T>): T? = map.remove(key) as T?

    private fun isPropertyKeyMissing(any: Any?, marker: Marker) = any == null && !map.containsKey(marker)
}
