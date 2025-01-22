package net.igsoft.typeutils.pipeline

import net.igsoft.typeutils.property.MutableTypedProperties
import net.igsoft.typeutils.property.DefaultTypedProperties

class Context : MutableTypedProperties by DefaultTypedProperties() {
    internal var pipeline: Pipeline? = null

    fun invokeNextProcessor() {
        pipeline?.process(this)
    }
}
