package net.igsoft.typeutils.marker

object Markers {
    fun markerEquals(marker: Marker, other: Any?): Boolean {
        if (marker === other) return true
        if (other !is Marker) return false

        return marker.id == other.id
    }

    fun markerHashCode(marker: Marker) = marker.id.hashCode()

    fun markerToString(anyMarker: Marker, id: Any, clazz: Class<*>) = "${anyMarker::class.java.simpleName}(id=$id, clazz=${clazz.canonicalName})"
}
