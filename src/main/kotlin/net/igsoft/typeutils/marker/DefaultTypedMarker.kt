package net.igsoft.typeutils.marker

import kotlin.properties.ReadOnlyProperty

open class DefaultTypedMarker<T> internal constructor(override val clazz: Class<T>, override val id: Any) : TypedMarker<T>, AbstractMarker(clazz, id) {
    companion object {
        fun <T> create(clazz: Class<T>, id: Any) =
            DefaultTypedMarker(clazz, id)

        inline fun <reified T> create(id: Any) =
            create(T::class.java, id)

        inline fun <reified T> create(): ReadOnlyProperty<Any?, DefaultTypedMarker<T>> {
            return ReadOnlyProperty { _, property -> create(T::class.java, property.name) }
        }
    }
}
