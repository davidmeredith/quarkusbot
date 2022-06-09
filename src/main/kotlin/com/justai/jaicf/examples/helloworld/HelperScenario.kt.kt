package com.justai.jaicf.examples.helloworld

import com.justai.jaicf.activator.catchall.catchAll
//import com.justai.jaicf.activator.dialogflow.dialogflow
import com.justai.jaicf.builder.Scenario
//import com.justai.jaicf.channel.alexa.activator.alexaIntent
import com.justai.jaicf.reactions.Reactions

val HelperScenario = Scenario {

    state("ask4name") {
        activators {
            catchAll() // always activate
//            intent("name")
        }

        action {
            var name: String? = null
            // check which activator actually fired (dialogFlow, alexa, catchAll):

//            activator.dialogflow?.run {
//                name = slots["name"]?.stringValue
//            }
//            activator.alexaIntent?.run {
//                name = slots["firstName"]?.value
//            }

            activator.catchAll?.run {
                name = request.input // blocks for input
            }

            if (name == "blah" || name.isNullOrBlank()) {
                reactions.say("Sorry, I didn't get it. Could you repeat please?")
            } else {
                reactions.goBack(name)
            }
        }
    }
}

fun Reactions.askForName(
    question: String,
    callbackState: String
) {
    say(question) // available as we're in an ext func of Reactions
    changeState("/helper/ask4name", callbackState)
}