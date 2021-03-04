package com.justai.jaicf.core.test.hooks

import com.justai.jaicf.hook.BeforeActivationHook
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.test.ScenarioTest
import org.junit.jupiter.api.Test

private val withBeforeActivationHook = object : Scenario() {
    init {
        handle<BeforeActivationHook> {
            it.setRequestInput(it.request.input.removePrefix("bad-prefix-"))
        }
        state("error") {
            activators { regex("some-query") }
            action { reactions.say("ok") }
        }
    }
}

class BeforeActivationHookTest : ScenarioTest(withBeforeActivationHook.model) {

    @Test
    fun `should modify input and process`() {
        query("bad-prefix-some-query") responds "ok"
    }
}
