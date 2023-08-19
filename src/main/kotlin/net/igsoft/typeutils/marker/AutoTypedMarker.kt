package net.igsoft.typeutils.marker

import net.igsoft.typeutils.generator.IntGenerator

@Suppress("unused")
class AutoTypedMarker<T> internal constructor(override val clazz: Class<T>, override val id: Any) :
    DefaultTypedMarker<T>(clazz, id) {

    companion object {
        private val intGenerator = IntGenerator()

        @JvmStatic
        fun <T> create(clazz: Class<T>) =
            AutoTypedMarker(clazz, intGenerator.next())

        inline fun <reified T> create() =
            create(T::class.java)
    }
}
