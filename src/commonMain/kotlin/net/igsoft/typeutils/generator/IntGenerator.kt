package net.igsoft.typeutils.generator

import kotlinx.atomicfu.atomic

class IntGenerator(sequenceStart: Int = Int.MIN_VALUE) : Generator<Int> {
    private val counter = atomic(sequenceStart)

    override fun next(): Int = counter.getAndIncrement()
}
