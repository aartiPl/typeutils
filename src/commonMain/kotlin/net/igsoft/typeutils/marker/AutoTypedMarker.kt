package net.igsoft.typeutils.marker

@Suppress("unused")
open class AutoTypedMarker<T> protected constructor(
    override val id: Any,
    override val type: TypeRef<T>,
    override val label: String
) :
    DefaultTypedMarker<T>(id, type, label) {

    companion object {
        @PublishedApi
        internal fun generate(): Int = Markers.nextAutoMarkerId()

        inline fun <reified T> create(label: String? = null): AutoTypedMarker<T> {
            val id = generate()
            val type = typeRef<T>()
            return create(id, type, label)
        }

        @PublishedApi
        internal fun <T> create(id: Any, type: TypeRef<T>, label: String?): AutoTypedMarker<T> =
            AutoTypedMarker(
                id,
                type,
                label ?: Markers.markerDefaultLabel(DefaultTypedMarker::class.simpleName, id, type),
            )
    }
}
