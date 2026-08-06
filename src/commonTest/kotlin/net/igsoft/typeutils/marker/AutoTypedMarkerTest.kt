package net.igsoft.typeutils.marker

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEqualTo
import kotlin.test.Test

class AutoTypedMarkerTest {

    private class ExtendedAutoTypedMarker<T>(
        type: TypeRef<T>,
        label: String? = null,
    ) : AutoTypedMarker<T>(type, label)

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

    @Test
    fun `Assert that public factory and subclass use unique generated integer ids`() {
        val type = typeRef<String>()
        val marker = AutoTypedMarker.create<String>()
        val extendedMarker = ExtendedAutoTypedMarker(type, "extended marker")

        assertThat(marker.id).isInstanceOf<Int>()
        assertThat(extendedMarker.id).isInstanceOf<Int>()
        assertThat(marker.id).isNotEqualTo(extendedMarker.id)
        assertThat(extendedMarker.type).isEqualTo(type)
        assertThat(extendedMarker.label).isEqualTo("extended marker")
    }

    @Test
    fun `Assert that subclass uses the default auto marker label`() {
        val type = typeRef<String>()
        val marker = ExtendedAutoTypedMarker(type)

        assertThat(marker.label).isEqualTo(
            Markers.markerDefaultLabel(DefaultTypedMarker::class.simpleName, marker.id, type),
        )
    }
}
