package net.igsoft.typeutils.marker

import java.util.*

abstract class AbstractMarker(override val clazz: Class<*>, override val id: Any) : Marker {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractMarker

        return id == other.id && clazz == other.clazz
    }

    override fun hashCode(): Int {
        return Objects.hash(id, clazz)
    }

    override fun toString(): String {
        return "Marker(id=$id, clazz=$clazz)"
    }
}
