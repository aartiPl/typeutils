package net.igsoft.typeutils.property

import net.igsoft.typeutils.marker.Marker
import net.igsoft.typeutils.marker.TypedMarker

interface MutableTypedProperties : ImmutableTypedProperties, MutableMap<Marker, Any?> {
    fun <T> put(key: TypedMarker<T>, value: T): T

    fun <T> getOrPut(key: TypedMarker<T>, calculateValue: () -> T): T

    // Necessary to enable syntax: map[key] = value
    operator fun <T> set(key: TypedMarker<T>, value: T)
}
