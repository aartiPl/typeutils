package net.igsoft.typeutils.marker

open class DefaultMarker(override val id: Any, override val clazz: Class<*>) : Marker {
    constructor(marker: Marker) : this(marker.id, marker.clazz)

    final override fun equals(other: Any?): Boolean = Markers.markerEquals(this, other)
    final override fun hashCode(): Int = Markers.markerHashCode(this)
    override fun toString(): String = Markers.markerToString(this, id, clazz)
}
