package net.igsoft.typeutils.marker

@Suppress("unused")
open class DefaultTypedMarker<T> protected constructor(
    override val id: Any,
    override val type: TypeRef<T>,
    override val label: String
) : TypedMarker<T>, DefaultMarker(id, type, label) {

    //Copying constructor (de facto alias of marker)
    constructor(marker: TypedMarker<T>) : this(marker.id, marker.type, marker.label)

    companion object {
        inline fun <reified T> create(id: Any, label: String? = null): DefaultTypedMarker<T> {
            val type = typeRef<T>()
            return create(id, type, label)
        }

        @PublishedApi
        internal fun <T> create(id: Any, type: TypeRef<T>, label: String?): DefaultTypedMarker<T> =
            DefaultTypedMarker<T>(
                id,
                type,
                label ?: Markers.markerDefaultLabel(DefaultTypedMarker::class.simpleName, id, type),
            )
    }
}
