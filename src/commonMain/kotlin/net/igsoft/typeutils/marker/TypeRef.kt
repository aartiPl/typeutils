package net.igsoft.typeutils.marker

import kotlin.reflect.KType
import kotlin.reflect.typeOf

class TypeRef<T> @PublishedApi internal constructor(
    val kotlinType: KType,
)

@OptIn(ExperimentalStdlibApi::class)
inline fun <reified T> typeRef(): TypeRef<T> = TypeRef(typeOf<T>())
