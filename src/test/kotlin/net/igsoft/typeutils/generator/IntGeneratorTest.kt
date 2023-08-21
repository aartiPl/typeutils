package net.igsoft.typeutils.generator

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IntGeneratorTest {
    private lateinit var intGenerator: IntGenerator

    @BeforeEach
    fun setUp() {
        intGenerator = IntGenerator()
    }

    @Test
    fun `Assert that initial value in IntGenerator can be set`() {
        assertThat(IntGenerator(15).next()).isEqualTo(15)
    }

    @Test
    fun `Assert that next is generating consecutive numbers`() {
        var last = intGenerator.next()

        (0..100).forEach {
            val current = intGenerator.next()
            assertThat(current - last).isEqualTo(1)
            last = current
        }
    }
}
