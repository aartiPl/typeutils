package net.igsoft.typeutils.marker

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.junit.jupiter.api.Test

class DefaultTypedMarkerTest {
    @Test
    fun `Assert that TypedMarker can be created with property syntax`() {
        val someProperty by DefaultTypedMarker.create<String>()
        assertThat(someProperty).apply {
            prop(DefaultTypedMarker<String>::clazz).isEqualTo(String::class.java)
            prop(DefaultTypedMarker<String>::id).isEqualTo("someProperty")
        }
    }

    @Test
    fun `Assert that TypedMarker can be created manually`() {
        assertThat(DefaultTypedMarker(String::class.java, "s1")).apply {
            prop(DefaultTypedMarker<String>::clazz).isEqualTo(String::class.java)
            prop(DefaultTypedMarker<String>::id).isEqualTo("s1")
        }

        assertThat(DefaultTypedMarker.create<Int>("s2")).apply {
            prop(DefaultTypedMarker<Int>::clazz).isEqualTo(Integer::class.java)
            prop(DefaultTypedMarker<Int>::id).isEqualTo("s2")
        }
    }
}
