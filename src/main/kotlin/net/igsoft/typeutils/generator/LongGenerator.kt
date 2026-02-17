package net.igsoft.typeutils.generator

import java.util.concurrent.atomic.AtomicLong

class LongGenerator(sequenceStart: Long = Long.MIN_VALUE) : Generator<Long> {
    private val counter = AtomicLong(sequenceStart)

    override fun next(): Long = counter.getAndIncrement()
}
