package net.igsoft.typeutils.marker

@Suppress("unused")
open class AutoTypedMarker<T> protected constructor(
    override val id: Any,
    override val type: TypeRef<T>,
    override val label: String
) :
    DefaultTypedMarker<T>(id, type, label) {

    protected constructor(type: TypeRef<T>, label: String? = null) : this(createArguments(type, label))

    private constructor(arguments: Arguments<T>) : this(arguments.id, arguments.type, arguments.label)

    companion object {
        @PublishedApi
        internal fun <T> create(type: TypeRef<T>, label: String?): AutoTypedMarker<T> =
            AutoTypedMarker(type, label)

        inline fun <reified T> create(label: String? = null): AutoTypedMarker<T> {
            return create(typeRef(), label)
        }

        private fun <T> createArguments(type: TypeRef<T>, label: String?): Arguments<T> {
            val id = Markers.nextAutoMarkerId()
            return Arguments(
                id,
                type,
                label ?: Markers.markerDefaultLabel(DefaultTypedMarker::class.simpleName, id, type),
            )
        }
    }

    private class Arguments<T>(
        val id: Int,
        val type: TypeRef<T>,
        val label: String,
    )
}
