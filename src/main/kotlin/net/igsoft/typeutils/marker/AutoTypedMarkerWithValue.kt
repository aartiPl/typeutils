package net.igsoft.typeutils.marker

@Suppress("unused")
class AutoTypedMarkerWithValue<T, V> private constructor(id: Any, clazz: Class<T>, val value: V, label: String) :
    AutoTypedMarker<T>(id, clazz, label) {

    companion object {
        @JvmStatic
        fun <T, V> create(clazz: Class<T>, value: V, label: String?): AutoTypedMarkerWithValue<T, V> {
            val id = generate()
            return AutoTypedMarkerWithValue(
                id,
                clazz,
                value,
                label ?: Markers.markerDefaultLabel(AutoTypedMarkerWithValue::class.simpleName, id, clazz)
            )
        }

        inline fun <reified T, V> create(value: V, label: String? = null) =
            create(T::class.java, value, label)
    }
}
