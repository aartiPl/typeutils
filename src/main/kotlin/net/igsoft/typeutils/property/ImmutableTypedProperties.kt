package net.igsoft.typeutils.property

import net.igsoft.typeutils.marker.Marker
import net.igsoft.typeutils.marker.TypedMarker

interface ImmutableTypedProperties : Map<Marker, Any?>, Iterable<Map.Entry<Marker, Any?>> {
    operator fun <T> get(marker: TypedMarker<T>): T?
    fun <T> getValue(marker: TypedMarker<T>): T
    fun <T> getOrDefault(marker: TypedMarker<T>, defaultValue: T): T
    fun <T> getOrElse(marker: TypedMarker<T>, calculateValue: () -> T): T
}
