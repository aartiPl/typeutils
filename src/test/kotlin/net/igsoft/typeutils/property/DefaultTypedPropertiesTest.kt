package net.igsoft.typeutils.property

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.*
import net.igsoft.typeutils.marker.DefaultTypedMarker
import net.igsoft.typeutils.marker.Marker
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultTypedPropertiesTest {
    private lateinit var properties: MutableTypedProperties

    private val name by DefaultTypedMarker.create<String>()
    private val surname by DefaultTypedMarker.create<String>()
    private val age by DefaultTypedMarker.create<Int>()
    private val shoeSize by DefaultTypedMarker.create<Int>()

    @BeforeEach
    fun setUp() {
        properties = DefaultTypedProperties()

        properties[name] = NAME
        properties[surname] = SURNAME
        properties[age] = AGE
    }

    @Test
    fun `Create properties with configuration block`() {
        val properties = DefaultTypedProperties {
            put(name, NAME)
            put(surname, SURNAME)
            put(shoeSize, 25)
        }

        assertThat(properties.getValue(name)).isEqualTo(NAME)
        assertThat(properties.getValue(surname)).isEqualTo(SURNAME)
        assertThat(properties.getValue(shoeSize)).isEqualTo(25)
    }

    @Test
    fun `Assert that creation is possible`() {
        val properties = DefaultTypedProperties()

        assertThat(properties).isNotNull()
        assertThat(properties).apply {
            prop(DefaultTypedProperties::size).isEqualTo(0)
            prop(DefaultTypedProperties::keys).isEmpty()
            prop(DefaultTypedProperties::values).isEmpty()
            prop(DefaultTypedProperties::entries).isEmpty()
        }
    }

    @Test
    fun `Assert that getting properties is possible`() {
        assertThat(properties[name]).isEqualTo(NAME)
        assertThat(properties[surname]).isEqualTo(SURNAME)
        assertThat(properties[shoeSize]).isNull()

        val untypedFirstname: Marker = name
        val untypedSurname: Marker = surname
        val untypedShoeSize: Marker = shoeSize

        assertThat(properties[untypedFirstname]).isNotNull().isInstanceOf<String>().isEqualTo(NAME)
        assertThat(properties[untypedSurname]).isNotNull().isInstanceOf<String>().isEqualTo(SURNAME)
        assertThat(properties[untypedShoeSize]).isNull()
    }

    @Test
    fun `Assert that getting not null properties is possible`() {
        assertThat(properties.getValue(name)).isEqualTo(NAME)
        assertThat(properties.getValue(surname)).isEqualTo(SURNAME)
        assertFailure { properties.getValue(shoeSize) }.hasMessage("Marker DefaultTypedMarker(id=shoeSize, clazz=java.lang.Integer) is missing in the properties")

        val untypedFirstname: Marker = name
        val untypedSurname: Marker = surname
        val untypedShoeSize: Marker = shoeSize

        assertThat(properties.getValue(untypedFirstname)).isNotNull().isInstanceOf<String>().isEqualTo(NAME)
        assertThat(properties.getValue(untypedSurname)).isNotNull().isInstanceOf<String>().isEqualTo(SURNAME)
        assertFailure { properties.getValue(untypedShoeSize) }.hasMessage("Marker DefaultTypedMarker(id=shoeSize, clazz=java.lang.Integer) is missing in the properties")
    }

    @Test
    fun `Assert that putting new properties is possible`() {
        properties[shoeSize] = 32

        assertThat(properties).apply {
            prop(MutableTypedProperties::size).isEqualTo(4)
            prop(MutableTypedProperties::keys).isEqualTo(setOf(this@DefaultTypedPropertiesTest.name, surname, age, shoeSize))
            prop(MutableTypedProperties::values).containsExactlyInAnyOrder(NAME, SURNAME, 28, 32)
        }
    }

    @Test
    fun `Assert that removing properties is possible`() {
        properties.remove(age)

        assertThat(properties).apply {
            prop(MutableTypedProperties::size).isEqualTo(2)
            prop(MutableTypedProperties::keys).isEqualTo(setOf(this@DefaultTypedPropertiesTest.name, surname))
            prop(MutableTypedProperties::values).containsExactlyInAnyOrder(NAME, SURNAME)
        }
    }

    @Test
    fun `Assert that clearing map leaves it empty`() {
        properties.clear()

        assertThat(properties).isEmpty()
    }

    @Test
    fun `Assert iteration over the map is possible`() {
        for (item in properties) {
            assertThat(item.key).isIn(name, surname, age, shoeSize)
        }
    }

    @Test
    fun `TypedProperties can be compared`() {
        val properties1 = DefaultTypedProperties {
            put(name, NAME)
            put(surname, SURNAME)
            put(shoeSize, 25)
        }

        val properties2 = DefaultTypedProperties {
            put(name, NAME)
            put(surname, SURNAME)
            put(shoeSize, 25)
        }

        assertThat(properties1).isEqualTo(properties2)
        assertThat(properties1 as TypedProperties).isEqualTo(properties2 as MutableTypedProperties)
    }

    @Test
    fun `Assert that all properties can be merged`() {
        // Given
        val newProperties = DefaultTypedProperties()

        // When
        newProperties.merge(properties)

        // Then
        assertThat(newProperties).hasSize(3)
        assertThat(newProperties[name]).isEqualTo(NAME)
        assertThat(newProperties[surname]).isEqualTo(SURNAME)
        assertThat(newProperties[age]).isEqualTo(AGE)
    }

    @Test
    fun `Assert that some properties can be merged`() {
        // Given
        val newProperties = DefaultTypedProperties()

        // When
        newProperties.merge(properties, name, age)

        // Then
        assertThat(newProperties).hasSize(2)
        assertThat(newProperties[name]).isEqualTo(NAME)
        assertThat(newProperties[age]).isEqualTo(AGE)
        assertThat(newProperties[surname]).isEqualTo(null)
        assertThat(newProperties[shoeSize]).isEqualTo(null)
    }

    companion object {
        private const val NAME = "Gregory"
        private const val SURNAME = "Iksiński"
        private const val AGE = 28
    }
}
