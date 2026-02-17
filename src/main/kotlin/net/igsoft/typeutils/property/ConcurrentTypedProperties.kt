package net.igsoft.typeutils.property

import java.util.concurrent.ConcurrentHashMap

class ConcurrentTypedProperties(
    typedProperties: TypedProperties? = null,
    configBlock: MutableTypedProperties.() -> Unit = {}
) : DefaultTypedProperties(run {
    ConcurrentHashMap(typedProperties?.entries?.associate { it.key to it.value }?.toMutableMap() ?: mutableMapOf())
}, configBlock)
