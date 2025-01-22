package net.igsoft.typeutils.property

import net.igsoft.typeutils.marker.Marker
import net.igsoft.typeutils.marker.TypedMarker

interface MutableTypedProperties : TypedProperties {
    fun <T> put(key: TypedMarker<T>, value: T): T

    fun putAll(from: TypedProperties)

    fun <T> getOrPut(key: TypedMarker<T>, calculateValue: () -> T): T

    // Transfer properties from source to current TypedProperties
    fun merge(source: TypedProperties)

    fun merge(source: TypedProperties, vararg keys: Marker)

    fun merge(source: TypedProperties, keys: Collection<Marker>)

    // Remove properties
    fun <T> remove(key: TypedMarker<T>): T?

    fun remove(key: Marker): Any?

    // Clear all the properties
    fun clear()

    // Necessary to enable syntax: map[key] = value
    operator fun <T> set(key: TypedMarker<T>, value: T)
}
