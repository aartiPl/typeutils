package net.igsoft.typeutils.generator

import java.util.concurrent.atomic.AtomicInteger

class IntGenerator(sequenceStart: Int = Int.MIN_VALUE) : Generator<Int> {
    private val counter = AtomicInteger(sequenceStart)

    override fun next(): Int = counter.getAndIncrement()
}
