package net.igsoft.typeutils.typedenum

@Suppress("unused")
abstract class TypedEnumCompanion<T> {
    private val registry = mutableMapOf<String, T>()

    protected fun register(name: String, value: T): T {
        check(registry.put(name, value) == null) { "Enum value '$name' is already registered" }
        return value
    }

    fun find(name: String): T? = registry[name]

    fun findOrThrow(name: String): T = find(name) ?: error("Can not find enum value for: '$name'")

    fun find(fn: (T) -> Boolean): T? = registry.values.find(fn)

    protected fun findName(instance: T) =
        registry.entries.firstOrNull { it.value == instance }?.key
            ?: error("Enum instance is not registered")
}
