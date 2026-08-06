package net.igsoft.typeutils.marker

@Suppress("unused")
class AutoTypedMarkerWithValue<T, V> private constructor(id: Any, type: TypeRef<T>, val value: V, label: String) :
    AutoTypedMarker<T>(id, type, label) {

    companion object {
        inline fun <reified T, V> create(value: V, label: String? = null): AutoTypedMarkerWithValue<T, V> {
            val id = generate()
            val type = typeRef<T>()
            return create(id, type, value, label)
        }

        @PublishedApi
        internal fun <T, V> create(
            id: Any,
            type: TypeRef<T>,
            value: V,
            label: String?,
        ): AutoTypedMarkerWithValue<T, V> =
            AutoTypedMarkerWithValue(
                id,
                type,
                value,
                label ?: Markers.markerDefaultLabel(AutoTypedMarkerWithValue::class.simpleName, id, type),
            )
    }
}
