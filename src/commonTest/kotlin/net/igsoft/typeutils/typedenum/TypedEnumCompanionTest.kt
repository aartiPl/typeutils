package net.igsoft.typeutils.typedenum

import assertk.assertThat
import assertk.assertions.isSameInstanceAs
import net.igsoft.typeutils.marker.AutoTypedMarker
import net.igsoft.typeutils.marker.DefaultTypedMarker
import net.igsoft.typeutils.marker.TypedMarker
import kotlin.test.Test

class TypedEnumCompanionTest {
    private class Decimal

    class SystemType<T : Any> private constructor(marker: TypedMarker<T>) : DefaultTypedMarker<T>(marker) {
        companion object : TypedEnumCompanion<SystemType<out Any>>() {
            val LINUX = register("LINUX", SystemType(AutoTypedMarker.create<String>()))
            val WINDOWS = register("WINDOWS", SystemType(AutoTypedMarker.create<Int>()))
            val CYGWIN = register("CYGWIN", SystemType(AutoTypedMarker.create<Decimal>()))
        }
    }

    class SystemTypeUntyped private constructor(marker: TypedMarker<String>) : DefaultTypedMarker<String>(marker) {
        companion object : TypedEnumCompanion<SystemTypeUntyped>() {
            val LINUX = register("LINUX", SystemTypeUntyped(AutoTypedMarker.create<String>()))
            val WINDOWS = register("WINDOWS", SystemTypeUntyped(AutoTypedMarker.create<String>()))
            val CYGWIN = register("CYGWIN", SystemTypeUntyped(AutoTypedMarker.create<String>()))
        }
    }

    @Test
    fun `Assert that finding new in enum works`() {
        assertThat(SystemType.find("LINUX")).isSameInstanceAs(SystemType.LINUX)
        assertThat(SystemType.find("CYGWIN")).isSameInstanceAs(SystemType.CYGWIN)
        assertThat(SystemType.find("WINDOWS")).isSameInstanceAs(SystemType.WINDOWS)

        assertThat(SystemTypeUntyped.find("LINUX")).isSameInstanceAs(SystemTypeUntyped.LINUX)
        assertThat(SystemTypeUntyped.find("CYGWIN")).isSameInstanceAs(SystemTypeUntyped.CYGWIN)
        assertThat(SystemTypeUntyped.find("WINDOWS")).isSameInstanceAs(SystemTypeUntyped.WINDOWS)
    }
}
