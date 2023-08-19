package net.igsoft.typeutils.marker

import kotlin.properties.ReadOnlyProperty

@Suppress("unused")
open class DefaultTypedMarker<T>(override val clazz: Class<T>, override val id: Any) : TypedMarker<T>,
    AbstractMarker(clazz, id) {

    //Copying constructor (de facto alias of marker)
    constructor(marker: TypedMarker<T>) : this(marker.clazz, marker.id)

    companion object {
        inline fun <reified T> create(id: Any) =
            DefaultTypedMarker(T::class.java, id)

        inline fun <reified T> create(): ReadOnlyProperty<Any?, DefaultTypedMarker<T>> {
            return ReadOnlyProperty { _, property -> DefaultTypedMarker(T::class.java, property.name) }
        }
    }
}
