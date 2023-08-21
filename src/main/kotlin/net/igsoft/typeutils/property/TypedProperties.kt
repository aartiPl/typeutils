package net.igsoft.typeutils.property

import net.igsoft.typeutils.marker.Marker
import net.igsoft.typeutils.marker.TypedMarker

@Suppress("UNCHECKED_CAST", "unused")
class TypedProperties(private val map: MutableMap<Marker, Any?>) : MutableTypedProperties {

    override operator fun <T> set(marker: TypedMarker<T>, value: T?) {
        map[marker] = value
    }

    override fun get(key: Marker): Any? = map[key]

    override fun <T> get(marker: TypedMarker<T>): T? = map[marker] as? T

    override fun getValue(marker: Marker): Any?  {
        if (isPropertyKeyMissing(null, marker)) {
            throw NoSuchElementException("Marker $marker is missing in the properties")
        }

        return map.getValue(marker)
    }

    override fun <T> getValue(marker: TypedMarker<T>): T {
        val value = map[marker]

        if (isPropertyKeyMissing(value, marker)) {
            throw NoSuchElementException("Marker $marker is missing in the properties")
        }

        return value as T
    }

    override fun <T> getOrDefault(marker: TypedMarker<T>, defaultValue: T): T {
        val value = map[marker]

        if (isPropertyKeyMissing(value, marker)) {
            return defaultValue
        }

        return value as T
    }

    override fun <T> getOrElse(marker: TypedMarker<T>, calculateValue: () -> T): T {
        val value = map[marker]

        if (isPropertyKeyMissing(value, marker)) {
            return calculateValue()
        }

        return value as T
    }

    override fun <T> getOrPut(marker: TypedMarker<T>, calculateValue: () -> T): T {
        var value = map[marker]

        if (isPropertyKeyMissing(value, marker)) {
            value = calculateValue()
            map[marker] = value
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

    override fun iterator(): Iterator<Map.Entry<Marker, Any?>> = map.iterator()

    fun clear() {
        map.clear()
    }

    fun <T> put(key: TypedMarker<T>, value: T?): Any? = map.put(key, value)

    fun putAll(from: Map<out Marker, Any?>) = map.putAll(from)

    fun remove(key: Marker): Any? = map.remove(key)

    private fun isPropertyKeyMissing(any: Any?, marker: Marker) = any == null && !map.containsKey(marker)
}
