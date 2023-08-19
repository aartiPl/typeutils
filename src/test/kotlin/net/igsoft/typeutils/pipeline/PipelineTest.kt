package net.igsoft.typeutils.pipeline

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.igsoft.typeutils.marker.DefaultTypedMarker
import org.junit.jupiter.api.Test

class PipelineTest {
    private val firstname by DefaultTypedMarker.create<String>()
    private val lastname by DefaultTypedMarker.create<String>()
    private val age by DefaultTypedMarker.create<Int>()
    private val shoeSize by DefaultTypedMarker.create<Int?>()
    private val helloMessage by DefaultTypedMarker.create<String>()

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
