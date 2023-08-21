package net.igsoft.typeutils.globalcontext

import net.igsoft.typeutils.marker.TypedMarker
import net.igsoft.typeutils.property.ImmutableTypedProperties
import net.igsoft.typeutils.property.TypedProperties
import java.util.concurrent.ConcurrentHashMap

open class GlobalContextImplementation(private val context: TypedProperties = TypedProperties(ConcurrentHashMap())) :
    ImmutableTypedProperties by context {

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
