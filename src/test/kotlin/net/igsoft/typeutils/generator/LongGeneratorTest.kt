package net.igsoft.typeutils.generator

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LongGeneratorTest {
    private lateinit var longGenerator: LongGenerator

    @BeforeEach
    fun setUp() {
        longGenerator = LongGenerator()
    }

    @Test
    fun `Assert that initial value in IntGenerator can be set`() {
        assertThat(LongGenerator(15).next()).isEqualTo(15)
    }

    @Test
    fun `Assert that next is generating consecutive numbers`() {
        var last = longGenerator.next()

        (0..100).forEach {
            val current = longGenerator.next()
            assertThat(current - last).isEqualTo(1)
            last = current
        }
    }
}
