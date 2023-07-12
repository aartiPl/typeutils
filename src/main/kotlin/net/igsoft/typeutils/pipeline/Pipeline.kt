package net.igsoft.typeutils.pipeline

class Pipeline(private val processors: List<Processor>) : Processor {
    constructor(vararg processors: Processor) : this(processors.toList())

    private data class State(var previousPipeline: Pipeline?, var index: Int)

    private val stateMap: MutableMap<Any, State> = mutableMapOf()

    override fun process(context: Context) {
        val currentState = stateMap.getOrPut(context) {
            val newState = State(context.pipeline, -1)
            context.pipeline = this
            newState
        }

        if (currentState.index < processors.size - 1) {
            currentState.index = currentState.index + 1
            processors[currentState.index].process(context)
            currentState.index = currentState.index - 1
        } else {
            context.pipeline = currentState.previousPipeline
            stateMap.remove(context)
            context.pipeline?.process(context)
        }
    }
}
