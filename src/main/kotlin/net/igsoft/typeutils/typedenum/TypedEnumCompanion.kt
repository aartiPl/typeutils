package net.igsoft.typeutils.typedenum

import net.igsoft.typeutils.util.Utils
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

@Suppress("unused")
abstract class TypedEnumCompanion<T> {
    private val registry: MutableMap<String, KProperty1.Getter<out TypedEnumCompanion<T>, Any?>> = mutableMapOf()

    init {
        val typeClazzName = Utils.getClassOfParentTypeParameter(this::class.java, 0).simpleName

        this::class.declaredMemberProperties.filter { isEnumMember(it, typeClazzName) }.forEach {
            registry[it.name] = it.getter
        }
    }

    fun find(name: String): T? = resolveValue(registry[name])

    fun findOrThrow(name: String): T = find(name) ?: error("Can not find enum value for: '$name'")

    fun find(fn: (T) -> Boolean): T? {
        return resolveValue(registry.values.find { fn(resolveValue(it)!!) })
    }

    protected fun findName(instance: T) =
        registry.entries.firstOrNull { resolveValue(it.value) == instance }?.key
            ?: error("Enum instance is not registered")

    @Suppress("UNCHECKED_CAST")
    private fun resolveValue(property: KProperty1.Getter<out TypedEnumCompanion<T>, Any?>?) = property?.call(this) as T?

    private fun isEnumMember(property: KProperty1<out TypedEnumCompanion<T>, *>, typeClazzName: String) =
        property.isFinal && !property.returnType.isMarkedNullable && property.returnType.classifier.toString().substringAfterLast(".")
            .substringAfterLast("$") == typeClazzName
}
