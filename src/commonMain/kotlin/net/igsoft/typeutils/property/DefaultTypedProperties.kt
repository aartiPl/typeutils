package net.igsoft.typeutils.property

import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import net.igsoft.typeutils.marker.Marker
import net.igsoft.typeutils.marker.TypedMarker

@Suppress("UNCHECKED_CAST", "unused")
open class DefaultTypedProperties protected constructor(
    private val map: MutableMap<Marker, Any?>,
    configBlock: MutableTypedProperties.() -> Unit = {}
) : MutableTypedProperties {
    private val lock = SynchronizedObject()

    constructor(
        typedProperties: TypedProperties? = null,
        configBlock: MutableTypedProperties.() -> Unit = {}
    ) : this(typedProperties?.entries?.associate { it.key to it.value }?.toMutableMap() ?: mutableMapOf(), configBlock)

    init {
        configBlock()
    }

    override operator fun <T> set(key: TypedMarker<T>, value: T) = synchronized(lock) {
        map[key] = value
    }

    override fun get(key: Marker): Any? = synchronized(lock) { map[key] }

    override fun <T> get(key: TypedMarker<T>): T? = get(key as Marker) as? T

    override fun getValue(key: Marker): Any = synchronized(lock) {
        val value = map[key]
        if (value == null && !map.containsKey(key)) {
            throw NoSuchElementException("Marker '$key' is missing in the properties")
        }
        value!!
    }

    override fun <T> getValue(key: TypedMarker<T>): T = synchronized(lock) {
        val value = map[key]
        if (value == null && !map.containsKey(key)) {
            throw NoSuchElementException("Marker '$key' is missing in the properties")
        }
        value as T
    }

    override fun <T> getOrDefault(key: TypedMarker<T>, defaultValue: T): T = synchronized(lock) {
        if (map.containsKey(key)) map[key] as T else defaultValue
    }

    override fun <T> getOrElse(key: TypedMarker<T>, calculateValue: () -> T): T = synchronized(lock) {
        if (map.containsKey(key)) map[key] as T else calculateValue()
    }

    override fun <T> getOrPut(key: TypedMarker<T>, calculateValue: () -> T): T = synchronized(lock) {
        if (map.containsKey(key)) map[key] as T else calculateValue().also { map[key] = it }
    }

    override fun merge(source: TypedProperties) = merge(source, source.keys)

    override fun merge(source: TypedProperties, vararg keys: Marker) = merge(source, keys.toList())

    override fun merge(source: TypedProperties, keys: Collection<Marker>) = synchronized(lock) {
        keys.forEach { key -> map[key] = source[key] }
    }

    override val size: Int get() = synchronized(lock) { map.size }
    override val entries: MutableSet<MutableMap.MutableEntry<Marker, Any?>> get() = synchronized(lock) { map.entries }
    override val keys: MutableSet<Marker> get() = synchronized(lock) { map.keys }
    override val values: MutableCollection<Any?> get() = synchronized(lock) { map.values }
    override fun isEmpty(): Boolean = synchronized(lock) { map.isEmpty() }
    override fun containsValue(value: Any?): Boolean = synchronized(lock) { map.containsValue(value) }
    override fun containsKey(key: Marker): Boolean = synchronized(lock) { map.containsKey(key) }
    override fun clear() = synchronized(lock) { map.clear() }

    override fun <T> put(key: TypedMarker<T>, value: T): T? = synchronized(lock) { map.put(key, value) as T? }
    override fun putAll(from: TypedProperties) = synchronized(lock) { map.putAll(from) }
    override fun <T> remove(key: TypedMarker<T>): T? = synchronized(lock) { map.remove(key) as T? }
    override fun remove(key: Marker): Any? = synchronized(lock) { map.remove(key) }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this::class != other?.let { it::class }) return false
        other as DefaultTypedProperties
        return synchronized(lock) { map == other.map }
    }

    override fun hashCode(): Int = synchronized(lock) { map.hashCode() }

    override fun toString(): String = synchronized(lock) {
        buildString {
            appendLine("DefaultTypedProperties [size=${map.size}]")
            map.entries.forEach { appendLine("  * ${it.key} -> ${it.value}") }
        }
    }
}
