package net.igsoft.typeutils.pipeline

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.igsoft.typeutils.marker.DefaultTypedMarker
import kotlin.test.Test

class PipelineTest {
    private val firstname = DefaultTypedMarker.create<String>("firstname")
    private val lastname = DefaultTypedMarker.create<String>("lastname")
    private val age = DefaultTypedMarker.create<Int>("age")
    private val shoeSize = DefaultTypedMarker.create<Int?>("shoeSize")
    private val helloMessage = DefaultTypedMarker.create<String>("helloMessage")

    private val p1: Processor = object: Processor {
        override fun process(context: Context) {
            context[helloMessage] = context[firstname] + " " + context[lastname]
            context.invokeNextProcessor()
        }
    }

    private val p2 = object: Processor {
        override fun process(context: Context) {
            val shoeSize = context[shoeSize]
            val details = "age: " + context[age] + if (shoeSize != null) ", shoeSize: $shoeSize" else ""
            context[helloMessage] = context[helloMessage] + " [$details]"
            context.invokeNextProcessor()
        }
    }

    @Test
    fun `Create simple pipeline`() {
        val context = Context()
        context[firstname] = "Marcin"
        context[lastname] = "Iksiński"
        context[age] = 28

        val pipeline = Pipeline(p1, p2)

        pipeline.process(context)

        assertThat(context[helloMessage]).isEqualTo("Marcin Iksiński [age: 28]")

        context[shoeSize] = 32
        pipeline.process(context)

        assertThat(context[helloMessage]).isEqualTo("Marcin Iksiński [age: 28, shoeSize: 32]")
    }
}
