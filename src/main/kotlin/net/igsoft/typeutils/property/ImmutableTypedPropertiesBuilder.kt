package net.igsoft.typeutils.property

import net.igsoft.typeutils.marker.TypedMarker

@Suppress("unused")
class ImmutableTypedPropertiesBuilder {
    private val properties = TypedProperties(mutableMapOf())

    fun <T> putProperty(key: TypedMarker<T>, value: T) = apply {
        properties[key] = value
    }

    fun build(): ImmutableTypedProperties = properties
}
