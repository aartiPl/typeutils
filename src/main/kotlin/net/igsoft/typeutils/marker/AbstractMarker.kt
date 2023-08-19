package net.igsoft.typeutils.marker

abstract class AbstractMarker(override val clazz: Class<*>, override val id: Any) : Marker {
    override fun equals(other: Any?): Boolean = Markers.markerEquals(this, other)
    override fun hashCode(): Int = Markers.markerHashCode(this)
    override fun toString(): String = "Marker(id=$id, clazz=$clazz)"
}
