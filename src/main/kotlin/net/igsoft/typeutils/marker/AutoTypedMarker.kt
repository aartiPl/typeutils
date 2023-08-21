package net.igsoft.typeutils.marker

import net.igsoft.typeutils.generator.IntGenerator

@Suppress("unused")
class AutoTypedMarker<T> internal constructor(override val id: Any, override val clazz: Class<T>) :
    DefaultTypedMarker<T>(id, clazz) {

    companion object {
        private val intGenerator = IntGenerator()

        @JvmStatic
        fun <T> create(clazz: Class<T>) =
            AutoTypedMarker(intGenerator.next(), clazz)

        inline fun <reified T> create() =
            create(T::class.java)
    }
}
