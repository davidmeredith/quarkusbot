package com.justai.jaicf.examples.helloworld

//import com.justai.jaicf.activator.dialogflow.dialogflow
//import com.justai.jaicf.activator.lex.lex
import com.justai.jaicf.builder.Scenario
//import com.justai.jaicf.channel.aimybox.AimyboxEvent
//import com.justai.jaicf.channel.aimybox.aimybox
//import com.justai.jaicf.channel.alexa.alexa
//import com.justai.jaicf.channel.alexa.intent
//import com.justai.jaicf.channel.alexa.model.AlexaEvent
//import com.justai.jaicf.channel.alexa.model.AlexaIntent
//import com.justai.jaicf.channel.facebook.facebook
//import com.justai.jaicf.channel.googleactions.dialogflow.DialogflowIntent
//import com.justai.jaicf.channel.telegram.telegram
//import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState

val HelloWorldScenario = Scenario {

    append(context = "helper", HelperScenario)
    state("main") {

        activators {
            catchAll()
        }

        action {
            var name = context.client["name"]
            var pet = context.client["has_pet"]

            if (name == null) {
                reactions.say("Hi there")
                reactions.askForName("What is your name? (try entering 'blah' first)", "get_name")
            } else {
                reactions.run {
                    //sayRandom("Hello $name!", "Hi $name!", "Glad to hear you $name!")
                    say("So $name, your pet status is: $pet. (Type 'quit' to restart)")
                }
            }
        }

        state("utterance_not_known", modal = true) {
            activators {
                catchAll()
            }

            action {
                reactions.apply {
                    sayRandom("What?", "Sorry, I didn't get that.")
                    say("Could you repeat please?")
                    changeState("/")
                }
            }
        }

        state("get_name") {
            action {
                context.client["name"] = context.result
                reactions.say("Nice to meet you ${context.result}")
                reactions.go("/main/has_pet")
            }
        }

        state("has_pet") {
            action {
                reactions.say("Do you have a pet?")
                context.client["has_pet"] = "no"
            }
            state("yes"){
                activators {
                    regex("^[\\s]*y|Y|yes")
                }
                action {
                    context.client["has_pet"] = "yes"
                    reactions.apply {
                        say("You're so cool")
                        go("/")
                    }
                }
            }
            state("no"){
                activators {
                    regex("^[\\s]*n|N|no")
                }
                action {
                    context.client["has_pet"] = "no"
                    reactions.apply {
                        say("You need a pet in your life to be cool...")
                        go("/")
                    }
                }
            }
            state("inner_confirm_YorN_fallback",modal = true) {
                activators {
                    catchAll()
                }
                action {
                    reactions.apply {
                        say("Please say yes or no")
                        go("/main/has_pet")
                    }
                }
            }

        }

        state("cancel", noContext = true){
           activators {
               regex("quit")
           }
            action {
                context.client["name"] = null
                context.client["has_pet"] = null
                reactions.say("Bye bye!")
                reactions.go("/")
            }
        }
    }
}

/*
    state("main") {

        activators {
            catchAll()
            event(AlexaEvent.LAUNCH)
            event(DialogflowIntent.WELCOME)
            event(AimyboxEvent.START)
        }

        action {
            var name = context.client["name"]

            if (name == null) {
                telegram {
                    name = request.message.chat.firstName ?: request.message.chat.username
                }
                facebook {
                    name = reactions.queryUserProfile()?.firstName()
                }
            }

            if (name == null) {
                reactions.askForName("Hello! What is your name?", "get_name")
            } else {
                reactions.run {
                    image("https://www.bluecross.org.uk/sites/default/files/d8/assets/images/118809lprLR.jpg")
                    sayRandom("Hello $name!", "Hi $name!", "Glad to hear you $name!")
                    buttons("Mew" toState "/mew", "Wake up" toState "/wakeup")
                    aimybox?.endConversation()
                }
            }
        }

        state("inner", modal = true) {
            activators {
                catchAll()
            }

            action {
                reactions.apply {
                    sayRandom("What?", "Sorry, I didn't get that.")
                    say("Could you repeat please?")
                    changeState("/")
                }
            }
        }

        state("get_name") {
            action {
                context.client["name"] = context.result
                reactions.say("Nice to meet you ${context.result}")
            }
        }
    }

    state("stop") {
        activators {
            intent(AlexaIntent.STOP)
        }

        action(alexa.intent) {
            reactions.endSession("See you latter! Bye bye!")
        }
    }

    state("mew") {
        activators {
            regex("mew")
        }

        action {
            reactions.image("https://www.bluecross.org.uk/sites/default/files/d8/assets/images/118809lprLR.jpg")
        }
    }

    state("wakeup") {
        activators {
            intent("wake_up")
        }

        action(dialogflow) {
            val dt = activator.slots["date-time"]
            reactions.say("Okay! I'll wake you up ${dt?.stringValue}")
        }
    }

    state("coffee") {
        activators {
            intent("OrderCoffee")
        }

        action(lex) {
            val size = activator.slots["Size"]
            val type = activator.slots["Type"]
            reactions.say("You ordered a $size cup of $type coffee.")
        }
    }

    state("cancel") {
        activators {
            intent("cancel")
        }
        action {
            reactions.say("Okay, canceling.")
        }
    }
}
*/
