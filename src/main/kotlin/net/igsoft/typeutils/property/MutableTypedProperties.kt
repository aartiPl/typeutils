package net.igsoft.typeutils.property

import net.igsoft.typeutils.marker.TypedMarker

interface MutableTypedProperties : ImmutableTypedProperties {
    operator fun <T> set(marker: TypedMarker<T>, value: T?)
    fun <T> getOrPut(marker: TypedMarker<T>, calculateValue: () -> T): T
}
