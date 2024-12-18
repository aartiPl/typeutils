package net.igsoft.typeutils.property

import net.igsoft.typeutils.marker.DefaultTypedMarker
import org.junit.jupiter.api.Test

class TypedPropertiesTypeCheckTest {
    private val name by DefaultTypedMarker.create<String>()

    @Test
    fun `Assert that type checking is working`() {
        //TODO: there is a need for runtime compilation to create correct type checking tests
        val properties = TypedProperties()
        properties[name] = "55"
    }
}
