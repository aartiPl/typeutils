package net.igsoft.typeutils.generator

import kotlinx.atomicfu.atomic

class LongGenerator(sequenceStart: Long = Long.MIN_VALUE) : Generator<Long> {
    private val counter = atomic(sequenceStart)

    override fun next(): Long = counter.getAndIncrement()
}
