package net.igsoft.typeutils.marker

import kotlin.properties.ReadOnlyProperty

open class TypedMarker<T> internal constructor(override val clazz: Class<T>, override val id: Any) : Marker(clazz, id) {
    companion object {
        fun <T> create(clazz: Class<T>, id: Any) =
            TypedMarker(clazz, id)

        inline fun <reified T> create(id: Any) =
            create(T::class.java, id)

        inline fun <reified T> create(): ReadOnlyProperty<Any?, TypedMarker<T>> {
            return ReadOnlyProperty { _, property -> create(T::class.java, property.name) }
        }
    }
}
