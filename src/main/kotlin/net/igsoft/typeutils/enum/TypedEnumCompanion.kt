package net.igsoft.typeutils.enum

import kotlin.properties.ReadOnlyProperty

@Suppress("unused")
abstract class TypedEnumCompanion<T> {
    private val registry: MutableMap<String, T> = mutableMapOf()

    fun find(name: String): T? = registry[name]
    fun findOrThrow(name: String): T =
        find(name) ?: throw IllegalArgumentException("Can not find enum value for: '$name'")

    protected fun <V : T> register(name: String, instance: V): V {
        registry[name] = instance
        return instance
    }

    protected fun <V : T> register(instance: V): ReadOnlyProperty<Any?, V> {
        return ReadOnlyProperty { _, property ->
            registry[property.name] = instance
            instance
        }
    }

    protected fun findName(instance: T) =
        registry.entries.firstOrNull { it.value == instance }?.key ?: error("Enum not registered")
}
