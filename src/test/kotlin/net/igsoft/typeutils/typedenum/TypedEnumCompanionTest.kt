package net.igsoft.typeutils.typedenum

import assertk.assertThat
import assertk.assertions.isSameAs
import net.igsoft.typeutils.marker.AutoTypedMarker
import net.igsoft.typeutils.marker.DefaultTypedMarker
import net.igsoft.typeutils.marker.TypedMarker
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TypedEnumCompanionTest {
    class SystemType<T> private constructor(marker: TypedMarker<T>) : DefaultTypedMarker<T>(marker) {
        companion object : TypedEnumCompanion<SystemType<out Any>>() {
            val LINUX = SystemType(AutoTypedMarker.create<String>())
            val WINDOWS = SystemType(AutoTypedMarker.create<Int>())
            val CYGWIN = SystemType(AutoTypedMarker.create<BigDecimal>())
        }
    }

    class SystemTypeUntyped private constructor(marker: TypedMarker<String>) : DefaultTypedMarker<String>(marker) {
        companion object : TypedEnumCompanion<SystemTypeUntyped>() {
            val LINUX = SystemTypeUntyped(AutoTypedMarker.create<String>())
            val WINDOWS = SystemTypeUntyped(AutoTypedMarker.create<String>())
            val CYGWIN = SystemTypeUntyped(AutoTypedMarker.create<String>())
        }
    }

    @Test
    fun `Assert that finding new in enum works`() {
        assertThat(SystemType.find("LINUX")).isSameAs(SystemType.LINUX)
        assertThat(SystemType.find("CYGWIN")).isSameAs(SystemType.CYGWIN)
        assertThat(SystemType.find("WINDOWS")).isSameAs(SystemType.WINDOWS)

        assertThat(SystemTypeUntyped.find("LINUX")).isSameAs(SystemTypeUntyped.LINUX)
        assertThat(SystemTypeUntyped.find("CYGWIN")).isSameAs(SystemTypeUntyped.CYGWIN)
        assertThat(SystemTypeUntyped.find("WINDOWS")).isSameAs(SystemTypeUntyped.WINDOWS)
    }
}
