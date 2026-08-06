package net.igsoft.typeutils.property

class ConcurrentTypedProperties(
    typedProperties: TypedProperties? = null,
    configBlock: MutableTypedProperties.() -> Unit = {}
) : DefaultTypedProperties(run {
    typedProperties?.entries?.associate { it.key to it.value }?.toMutableMap() ?: mutableMapOf()
}, configBlock)
