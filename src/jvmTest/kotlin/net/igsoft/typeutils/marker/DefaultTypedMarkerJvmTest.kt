package net.igsoft.typeutils.marker

import kotlin.test.Test
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning

class DefaultTypedMarkerJvmTest {
    @Test
    fun `Assert that equals and hashCode work correctly`() {
        EqualsVerifier.forClass(DefaultTypedMarker::class.java)
            .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
            .verify()
    }
}
