package net.igsoft.typeutils.pipeline

import net.igsoft.typeutils.property.MutableTypedProperties
import net.igsoft.typeutils.property.TypedProperties

class Context : MutableTypedProperties by TypedProperties(mutableMapOf()) {
    internal var pipeline: Pipeline? = null

    fun invokeNextProcessor() {
        pipeline?.process(this)
    }
}
