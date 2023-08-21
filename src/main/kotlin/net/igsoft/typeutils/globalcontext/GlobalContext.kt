package net.igsoft.typeutils.globalcontext

import net.igsoft.typeutils.marker.Marker
import net.igsoft.typeutils.marker.TypedMarker
import net.igsoft.typeutils.property.ImmutableTypedProperties
import net.igsoft.typeutils.property.TypedProperties
import java.util.concurrent.ConcurrentHashMap

@Suppress("unused")
object GlobalContext : ImmutableTypedProperties {
    private val context: TypedProperties = TypedProperties(ConcurrentHashMap())

    fun <T> register(key: TypedMarker<T>, value: T) {
        require(!context.containsKey(key)) {
            "Key '$key' was already registered before"
        }

        context[key] = value
    }

    fun <T> registerOrReplace(key: TypedMarker<T>, value: T) {
        context[key] = value
    }

    fun <T> getOrError(
        marker: TypedMarker<T>,
        message: String = "Key '${marker} doesn't exist in the GlobalContext'"
    ): T {
        return getOrElse(marker) { error(message) }
    }

    override fun get(key: Marker): Any? = context[key]

    override operator fun <T> get(marker: TypedMarker<T>) = context[marker]

    override fun getValue(marker: Marker): Any? = context.getValue(marker)

    override fun <T> getValue(marker: TypedMarker<T>): T = context.getValue(marker)

    override fun <T> getOrDefault(marker: TypedMarker<T>, defaultValue: T): T =
        context.getOrDefault(marker, defaultValue)

    override fun <T> getOrElse(marker: TypedMarker<T>, calculateValue: () -> T): T =
        context.getOrElse(marker, calculateValue)

    override val entries: Set<Map.Entry<Marker, Any?>> get() = context.entries

    override val keys: Set<Marker> get() = context.keys

    override val size: Int get() = context.size

    override val values: Collection<Any?> get() = context.values

    override fun containsKey(key: Marker): Boolean = context.containsKey(key)

    override fun containsValue(value: Any?): Boolean = context.containsValue(value)

    override fun isEmpty(): Boolean = context.isEmpty()

    override fun iterator(): Iterator<Map.Entry<Marker, Any?>> = context.iterator()
}
