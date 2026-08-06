package net.igsoft.typeutils.property

import net.igsoft.typeutils.marker.DefaultTypedMarker
import kotlin.test.Test

class DefaultTypedPropertiesTypeCheckTest {
    private val name = DefaultTypedMarker.create<String>("name")

    @Test
    fun `Assert that type checking is working`() {
        //TODO: there is a need for runtime compilation to create correct type checking tests
        val properties = DefaultTypedProperties()
        properties[name] = "55"
    }
}
