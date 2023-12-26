package net.igsoft.typeutils.marker

import net.igsoft.typeutils.generator.IntGenerator

@Suppress("unused")
class NamedAutoTypedMarker<T> internal constructor(override val id: Any, override val clazz: Class<T>, val name: String) :
    DefaultTypedMarker<T>(id, clazz) {

    companion object {
        private val intGenerator = IntGenerator()

        @JvmStatic
        fun <T> create(name: String, clazz: Class<T>) = NamedAutoTypedMarker(intGenerator.next(), clazz, name)

        inline fun <reified T> create(name: String) = create(name, T::class.java)
    }
}
