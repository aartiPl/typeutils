package net.igsoft.typeutils.marker

interface TypedMarker<T> : Marker {
    override val clazz: Class<T>
    override val id: Any
}
