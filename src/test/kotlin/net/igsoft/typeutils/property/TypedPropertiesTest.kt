package net.igsoft.typeutils.property

import assertk.assertThat
import assertk.assertions.*
import net.igsoft.typeutils.marker.DefaultTypedMarker
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TypedPropertiesTest {
    private lateinit var properties: TypedProperties
    private val firstname by DefaultTypedMarker.create<String>()
    private val surname by DefaultTypedMarker.create<String>()
    private val age by DefaultTypedMarker.create<Int>()
    private val shoeSize by DefaultTypedMarker.create<Int>()

    @BeforeEach
    fun setUp() {
        properties = TypedProperties(mutableMapOf())

        properties[firstname] = "Gregory"
        properties[surname] = "Iksiński"
        properties[age] = 28
    }

    @Test
    fun `Assert that creation is possible`() {
        val properties = TypedProperties(mutableMapOf())

        assertThat(properties).isNotNull()
        assertThat(properties).apply {
            prop(TypedProperties::size).isEqualTo(0)
            prop(TypedProperties::keys).isEmpty()
            prop(TypedProperties::values).isEmpty()
            prop(TypedProperties::entries).isEmpty()
        }
    }

    @Test
    fun `Assert that putting new properties is possible`() {
        properties[shoeSize] = 32

        assertThat(properties).apply {
            prop(TypedProperties::size).isEqualTo(4)
            prop(TypedProperties::keys).isEqualTo(setOf(firstname, surname, age, shoeSize))
            prop(TypedProperties::values).containsExactlyInAnyOrder("Gregory", "Iksiński", 28, 32)
        }
    }

    @Test
    fun `Assert that removing properties is possible`() {
        properties.remove(age)

        assertThat(properties).apply {
            prop(TypedProperties::size).isEqualTo(2)
            prop(TypedProperties::keys).isEqualTo(setOf(firstname, surname))
            prop(TypedProperties::values).containsExactlyInAnyOrder("Gregory", "Iksiński")
        }
    }
}
