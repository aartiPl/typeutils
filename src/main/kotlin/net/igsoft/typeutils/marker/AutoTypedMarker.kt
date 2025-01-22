package net.igsoft.typeutils.marker

import net.igsoft.typeutils.generator.IntGenerator

@Suppress("unused")
open class AutoTypedMarker<T> protected constructor(override val id: Any, override val clazz: Class<T>, override val label: String) :
    DefaultTypedMarker<T>(id, clazz, label) {

    companion object {
        private val intGenerator = IntGenerator()

        @JvmStatic
        protected fun generate(): Int = intGenerator.next()

        @JvmStatic
        fun <T> create(clazz: Class<T>, label: String? = null): AutoTypedMarker<T> {
            val id = generate()

            return AutoTypedMarker(
                id,
                clazz,
                label ?: Markers.markerDefaultLabel(DefaultTypedMarker::class.simpleName, id, clazz)
            )
        }

        inline fun <reified T> create(label: String?= null) = create(T::class.java, label)
    }
}
