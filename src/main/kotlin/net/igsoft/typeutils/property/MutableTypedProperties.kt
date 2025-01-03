package net.igsoft.typeutils.property

import net.igsoft.typeutils.marker.Marker
import net.igsoft.typeutils.marker.TypedMarker

interface MutableTypedProperties : ImmutableTypedProperties {
    fun <T> put(key: TypedMarker<T>, value: T): T

    fun putAll(from: ImmutableTypedProperties)

    fun <T> getOrPut(key: TypedMarker<T>, calculateValue: () -> T): T

    fun transfer(source: ImmutableTypedProperties, vararg keys: Marker)

    fun transfer(source: ImmutableTypedProperties, keys: Collection<Marker>)

    fun <T> remove(key: TypedMarker<T>): T?

    fun remove(key: Marker): Any?

    fun clear()

    // Necessary to enable syntax: map[key] = value
    operator fun <T> set(key: TypedMarker<T>, value: T)
}
