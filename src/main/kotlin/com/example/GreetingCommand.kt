package com.example

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import com.justai.jaicf.channel.ConsoleChannel
import com.justai.jaicf.examples.helloworld.helloWorldBot

@Command(name = "greeting", mixinStandardHelpOptions = true)
class GreetingCommand : Runnable {

    @Parameters(paramLabel = "<name>", defaultValue = "david", description = ["Your name."])
    var name: String? = null
    override fun run() {
        System.out.printf("Greetings %s, go go commando!\n", name)

        println("This demo shows how to build a native CLI bot using JAIF, Quarkus & Picocli.\n" +
                "Only use 'yes' and 'no' to answer pet related questions (for simplicity, " +
                "the demo does not use NLU, only regex).")
        println("Run with '-v' option for instructions. Ctrl+c to stop.")
        println("Type something in the console to start...")
        runConsoleChannel()
    }

}
fun runConsoleChannel(){
    // Extend HttpBotChannelServlet and pass an instance of channel (as per JAIF Spring doc).
    // Pass a BotEngine implementation to the channel instance:
    ConsoleChannel(helloWorldBot).run()
}
