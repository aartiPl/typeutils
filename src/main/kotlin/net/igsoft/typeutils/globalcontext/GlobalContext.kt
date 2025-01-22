package net.igsoft.typeutils.globalcontext

import net.igsoft.typeutils.marker.TypedMarker
import net.igsoft.typeutils.property.ConcurrentTypedProperties
import net.igsoft.typeutils.property.MutableTypedProperties
import net.igsoft.typeutils.property.TypedProperties

open class GlobalContextImplementation(private val context: MutableTypedProperties = ConcurrentTypedProperties()) :
    TypedProperties by context {

    fun <T> register(key: TypedMarker<T>, value: T) {
        require(!context.containsKey(key)) {
            "Key '$key' was already registered before"
        }

        context[key] = value
    }

    fun <T> registerOrReplace(key: TypedMarker<T>, value: T) {
        context[key] = value
    }
}

object GlobalContext : GlobalContextImplementation()
