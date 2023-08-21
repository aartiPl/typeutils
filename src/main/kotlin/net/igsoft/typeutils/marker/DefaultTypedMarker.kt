package net.igsoft.typeutils.marker

import kotlin.properties.ReadOnlyProperty

@Suppress("unused")
open class DefaultTypedMarker<T>(override val id: Any, override val clazz: Class<T>) : TypedMarker<T>,
    AbstractMarker(id, clazz) {

    //Copying constructor (de facto alias of marker)
    constructor(marker: TypedMarker<T>) : this(marker.id, marker.clazz)

    companion object {
        inline fun <reified T> create(id: Any) =
            DefaultTypedMarker(id, T::class.java)

        inline fun <reified T> create(): ReadOnlyProperty<Any?, DefaultTypedMarker<T>> {
            return ReadOnlyProperty { _, property -> DefaultTypedMarker(property.name, T::class.java) }
        }
    }
}
