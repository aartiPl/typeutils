package net.igsoft.typeutils.marker

import net.igsoft.typeutils.generator.IntGenerator
import kotlin.reflect.KClass

object Markers {
    private val autoMarkerIdGenerator = IntGenerator()

    internal fun nextAutoMarkerId(): Int = autoMarkerIdGenerator.next()

    fun markerEquals(marker: Marker, other: Any?): Boolean {
        if (marker === other) return true
        if (other !is Marker) return false

        return marker.id == other.id
    }

    fun markerHashCode(marker: Marker) = marker.id.hashCode()

    fun markerDefaultLabel(markerClazz: String?, id: Any, type: TypeRef<*>): String =
        "${markerClazz ?: "anonymous"}(id=$id, type=${type.displayName()})"

    private fun TypeRef<*>.displayName(): String =
        (kotlinType.classifier as? KClass<*>)?.qualifiedName
            ?: kotlinType.toString()
}
