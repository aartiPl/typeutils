package net.igsoft.typeutils.marker

object Markers {
    fun markerEquals(marker: Marker, other: Any?): Boolean {
        if (marker === other) return true
        if (other !is Marker) return false

        return marker.id == other.id
    }

    fun markerHashCode(marker: Marker) = marker.id.hashCode()

    fun markerDefaultLabel(markerClazz: String?, id: Any, clazz: Class<*>): String =
        "${markerClazz ?: "anonymous"}(id=$id, clazz=${clazz.canonicalName})"
}
