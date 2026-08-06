package net.igsoft.typeutils.marker

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import kotlin.test.Test

class AutoTypedMarkerTest {

    @Test
    fun `Assert that creation of two AutoTypedMarkers generates unique 'id's`() {
        assertThat(AutoTypedMarker.create<Int>().id).isNotEqualTo(AutoTypedMarker.create<Int>().id)
    }

    @Test
    fun `Assert that nullable type arguments generate unique ids`() {
        assertThat(AutoTypedMarker.create<Int>().id).isNotEqualTo(AutoTypedMarker.create<Int?>().id)
    }

    @Test
    fun `Assert that label is correctly created`() {
        val marker: TypedMarker<String> = AutoTypedMarker.create("marker")

        assertThat(marker.label).isEqualTo("marker")
    }
}
