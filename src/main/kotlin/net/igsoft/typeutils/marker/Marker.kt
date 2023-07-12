package net.igsoft.typeutils.marker

import java.util.*

abstract class Marker(open val clazz: Class<*>, open val id: Any) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Marker

        return id == other.id && clazz == other.clazz
    }

    override fun hashCode(): Int {
        return Objects.hash(id, clazz)
    }

    override fun toString(): String {
        return "Marker(id=$id, clazz=$clazz)"
    }
}
