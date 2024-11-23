package net.igsoft.typeutils.property

import net.igsoft.typeutils.marker.Marker
import net.igsoft.typeutils.marker.TypedMarker

interface ImmutableTypedProperties : Map<Marker, Any?> {
    operator fun <T> get(key: TypedMarker<T>): T?

    fun getValue(key: Marker): Any?
    fun <T> getValue(key: TypedMarker<T>): T

    fun <T> getOrDefault(key: TypedMarker<T>, defaultValue: T): T
    fun <T> getOrElse(key: TypedMarker<T>, calculateValue: () -> T): T
}
