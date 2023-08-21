package net.igsoft.typeutils.marker

import assertk.assertThat
import assertk.assertions.isNotEqualTo
import org.junit.jupiter.api.Test

class AutoTypedMarkerTest {

    @Test
    fun `Assert that creation of two AutoTypedMarkers generates unique 'id's`() {
        assertThat(AutoTypedMarker.create<Int>().id).isNotEqualTo(AutoTypedMarker.create<Int>().id)
    }
}
