package net.igsoft.typeutils.marker

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
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
        assertThat(DefaultTypedMarker("s1", String::class.java)).apply {
            prop(DefaultTypedMarker<String>::clazz).isEqualTo(String::class.java)
            prop(DefaultTypedMarker<String>::id).isEqualTo("s1")
        }

        assertThat(DefaultTypedMarker.create<Int>("s2")).apply {
            prop(DefaultTypedMarker<Int>::clazz).isEqualTo(Integer::class.java)
            prop(DefaultTypedMarker<Int>::id).isEqualTo("s2")
        }
    }

    @Test
    fun `Assert that TypedMarker get other other TypedMarker and both will point to the same value`() {
        val typedMarker = DefaultTypedMarker.create<String>(5)

        assertThat(DefaultTypedMarker(typedMarker)).apply {
            prop(DefaultTypedMarker<String>::clazz).isEqualTo(String::class.java)
            prop(DefaultTypedMarker<String>::id).isEqualTo(5)
        }
    }

    @Test
    fun `Assert that TypedMarkers are always different by 'id' field (no duplicates)`() {
        var counter = 0

        val list = generateSequence {
            DefaultTypedMarker.create<Int>(counter++)
        }.take(100).toList()

        assertThat(list.size).isEqualTo(list.map { it.id }.distinct().count())
    }

    @Test
    fun `Assert that 'toString' works correctly`() {
        assertThat(DefaultTypedMarker.create<Int>("v1").toString()).isEqualTo("DefaultTypedMarker(id=v1, clazz=java.lang.Integer)")
        assertThat(DefaultTypedMarker.create<String>("v2").toString()).isEqualTo("DefaultTypedMarker(id=v2, clazz=java.lang.String)")
    }

    @Test
    fun `Assert that 'equals' and 'hashCode' work correctly`() {
        EqualsVerifier.forClass(DefaultTypedMarker::class.java)
            .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
            .verify()
    }
}
