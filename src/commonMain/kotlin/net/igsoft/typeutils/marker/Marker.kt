package net.igsoft.typeutils.marker

interface Marker {
    val id: Any
    val type: TypeRef<*>
    val label: String
}
