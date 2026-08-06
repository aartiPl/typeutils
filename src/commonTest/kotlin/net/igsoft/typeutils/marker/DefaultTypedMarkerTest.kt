package net.igsoft.typeutils.marker

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.reflect.typeOf
import kotlin.test.Test

@OptIn(ExperimentalStdlibApi::class)
class DefaultTypedMarkerTest {
    @Test
    fun `Assert that TypedMarker can be created with property syntax`() {
        val someProperty = DefaultTypedMarker.create<String>("someProperty", "someProperty")

        assertThat(someProperty.type.kotlinType).isEqualTo(typeOf<String>())
        assertThat(someProperty.id).isEqualTo("someProperty")
        assertThat(someProperty.label).isEqualTo("someProperty")
    }

    @Test
    fun `Assert that reified factories preserve String Int and nullable String descriptors`() {
        assertThat(DefaultTypedMarker.create<String>("s1").type.kotlinType).isEqualTo(typeOf<String>())
        assertThat(DefaultTypedMarker.create<Int>("s2").type.kotlinType).isEqualTo(typeOf<Int>())
        assertThat(DefaultTypedMarker.create<String?>("s3").type.kotlinType).isEqualTo(typeOf<String?>())
    }

    @Test
    fun `Assert that TypedMarker constructor can receive another TypedMarker and both will point to the same value`() {
        val typedMarker = DefaultTypedMarker.create<String>(5)
        val copiedMarker = DefaultTypedMarker(typedMarker)

        assertThat(copiedMarker.type.kotlinType).isEqualTo(typeOf<String>())
        assertThat(copiedMarker.id).isEqualTo(5)
        assertThat(copiedMarker.label).isEqualTo("DefaultTypedMarker(id=5, type=kotlin.String)")
    }

    @Test
    fun `Assert that TypedMarkers are always different by id field`() {
        var counter = 0

        val list = generateSequence {
            DefaultTypedMarker.create<Int>(counter++)
        }.take(100).toList()

        assertThat(list.size).isEqualTo(list.map { it.id }.distinct().count())
    }

    @Test
    fun `Assert that toString works correctly`() {
        assertThat(DefaultTypedMarker.create<Int>("v1").toString()).isEqualTo("DefaultTypedMarker(id=v1, type=kotlin.Int)")
        assertThat(DefaultTypedMarker.create<String>("v2").toString()).isEqualTo("DefaultTypedMarker(id=v2, type=kotlin.String)")
    }
}
