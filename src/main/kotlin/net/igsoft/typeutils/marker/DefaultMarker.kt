package net.igsoft.typeutils.marker

open class DefaultMarker protected constructor(
    override val id: Any,
    override val clazz: Class<*>,
    override val label: String
) : Marker {
    constructor(marker: Marker) : this(marker.id, marker.clazz, marker.label)

    final override fun equals(other: Any?): Boolean = Markers.markerEquals(this, other)
    final override fun hashCode(): Int = Markers.markerHashCode(this)
    override fun toString(): String = label
}
