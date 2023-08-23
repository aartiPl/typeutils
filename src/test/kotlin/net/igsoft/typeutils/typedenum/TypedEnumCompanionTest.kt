package net.igsoft.typeutils.typedenum

import assertk.assertThat
import assertk.assertions.isSameAs
import net.igsoft.typeutils.marker.AutoTypedMarker
import net.igsoft.typeutils.marker.DefaultTypedMarker
import net.igsoft.typeutils.marker.TypedMarker
import org.junit.jupiter.api.Test

class TypedEnumCompanionTest {
    class SystemType<T> private constructor(marker: TypedMarker<T>) : DefaultTypedMarker<T>(marker) {
        companion object : TypedEnumCompanion<SystemType<String>>() {
            val LINUX = SystemType(AutoTypedMarker.create<String>())
            val WINDOWS = SystemType(AutoTypedMarker.create<String>())
            val CYGWIN = SystemType(AutoTypedMarker.create<String>())
        }
    }

    @Test
    fun `Assert that finding new in enum works`() {
        assertThat(SystemType.find("LINUX")).isSameAs(SystemType.LINUX)
        assertThat(SystemType.find("CYGWIN")).isSameAs(SystemType.CYGWIN)
        assertThat(SystemType.find("WINDOWS")).isSameAs(SystemType.WINDOWS)
    }
}
