package net.igsoft.typeutils.marker

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import org.junit.jupiter.api.Test

class AutoTypedMarkerWithValueTest {

    @Test
    fun `Assert that creation of two AutoTypedMarkersWithValue generates unique 'id's`() {
        assertThat(AutoTypedMarkerWithValue.create<Int, Int>(5).id).isNotEqualTo(
            AutoTypedMarkerWithValue.create<Int, Int>(
                5
            ).id
        )
    }

    @Test
    fun `Assert that values can be read`() {
        // Given-When
        val marker1 = AutoTypedMarkerWithValue.create<Int, Int>(5)
        val marker2 = AutoTypedMarkerWithValue.create<Int, Int>(10)

        // Then
        assertThat(marker1.value).isEqualTo(5)
        assertThat(marker2.value).isEqualTo(10)
    }
}
