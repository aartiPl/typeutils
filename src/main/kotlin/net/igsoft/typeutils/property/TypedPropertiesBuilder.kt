package net.igsoft.typeutils.property

import net.igsoft.typeutils.marker.TypedMarker

@Suppress("unused")
class TypedPropertiesBuilder {
    private val properties = DefaultTypedProperties()

    fun <T> putProperty(key: TypedMarker<T>, value: T) = apply {
        properties[key] = value
    }

    fun build(): TypedProperties = properties
}
