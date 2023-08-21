package net.igsoft.typeutils.marker

abstract class AbstractMarker(override val id: Any, override val clazz: Class<*>) : Marker {
    final override fun equals(other: Any?): Boolean = Markers.markerEquals(this, other)
    final override fun hashCode(): Int = Markers.markerHashCode(this)
    override fun toString(): String = Markers.markerToString(this, id, clazz)
}
