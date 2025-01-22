package net.igsoft.typeutils.property

import net.igsoft.typeutils.marker.DefaultTypedMarker
import org.junit.jupiter.api.Test

class DefaultTypedPropertiesTypeCheckTest {
    private val name by DefaultTypedMarker.createWithPropertyNameId<String>()

    @Test
    fun `Assert that type checking is working`() {
        //TODO: there is a need for runtime compilation to create correct type checking tests
        val properties = DefaultTypedProperties()
        properties[name] = "55"
    }
}
