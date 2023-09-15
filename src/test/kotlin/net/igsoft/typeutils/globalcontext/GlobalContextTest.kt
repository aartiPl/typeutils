package net.igsoft.typeutils.globalcontext

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.igsoft.typeutils.marker.DefaultTypedMarker
import org.junit.jupiter.api.Test

data class Person(val firstName: String, val lastName: String, val age: Int)

class GlobalContextTest {
    private val person by DefaultTypedMarker.create<Person>()
    private val otherPerson by DefaultTypedMarker.create<Person>()

    @Test
    fun `Assert that we can register and read objects in GlobalContext`() {
        val personEntity = Person("Marcin", "Iksiński", 28)
        GlobalContext.register(person, personEntity)

        assertThat(GlobalContext[person]).isEqualTo(personEntity)
    }

    @Test
    fun `Assert that we can re-register objects in GlobalContext`() {
        val personEntity = Person("Marcin", "Iksiński", 28)
        val newPersonEntity = Person("Marcin", "Babiński", 52)

        GlobalContext.register(otherPerson, personEntity)
        assertThat(GlobalContext[otherPerson]).isEqualTo(personEntity)

        GlobalContext.registerOrReplace(otherPerson, newPersonEntity)
        assertThat(GlobalContext[otherPerson]).isEqualTo(newPersonEntity)
    }
}
