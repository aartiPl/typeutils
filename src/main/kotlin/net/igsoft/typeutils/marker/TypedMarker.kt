package net.igsoft.typeutils.marker

interface TypedMarker<T> : Marker {
    override val id: Any
    override val clazz: Class<T>
}
