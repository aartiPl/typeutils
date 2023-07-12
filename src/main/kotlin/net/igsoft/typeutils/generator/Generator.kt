package net.igsoft.typeutils.generator

interface Generator<T> {
    fun next(): T
}
