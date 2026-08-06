package net.igsoft.typeutils.marker

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import kotlin.reflect.typeOf
import kotlin.test.Test

class TypeRefTest {
    @Test
    fun `typeRef preserves a non-null Kotlin type`() {
        val reference = typeRef<String>()

        assertThat(reference.kotlinType).isEqualTo(typeOf<String>())
        assertThat(reference.kotlinType.isMarkedNullable).isFalse()
    }

    @Test
    fun `typeRef preserves nullable Kotlin type`() {
        val reference = typeRef<String?>()

        assertThat(reference.kotlinType).isEqualTo(typeOf<String?>())
        assertThat(reference.kotlinType.isMarkedNullable).isTrue()
    }
}
