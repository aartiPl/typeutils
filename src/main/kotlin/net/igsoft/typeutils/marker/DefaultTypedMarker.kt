package net.igsoft.typeutils.marker

@Suppress("unused")
open class DefaultTypedMarker<T> protected constructor(
    override val id: Any,
    override val clazz: Class<T>,
    override val label: String
) : TypedMarker<T>, DefaultMarker(id, clazz, label) {

    //Copying constructor (de facto alias of marker)
    constructor(marker: TypedMarker<T>) : this(marker.id, marker.clazz, marker.label)

    companion object {
        fun <T> create(id: Any, clazz: Class<T>, label: String? = null) =
            DefaultTypedMarker(
                id,
                clazz,
                label ?: Markers.markerDefaultLabel(DefaultTypedMarker::class.simpleName, id, clazz)
            )

        inline fun <reified T> create(id: Any, label: String? = null) =
            create(id, T::class.java, label)
    }
}
