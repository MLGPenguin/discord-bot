package com.learnspigot.bot.counting

import com.learnspigot.bot.database.counting.CountingManager
import com.learnspigot.bot.database.profile.getProfile
import com.learnspigot.bot.util.InvisibleEmbed
import net.dv8tion.jda.api.entities.User
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Optional
import revxrsal.commands.jda.actor.SlashCommandActor

class CountingCommand {

    @Command("countingstats")
    fun onCountingStatsCommand(actor: SlashCommandActor, @Optional target: User? = null) {
        if (target == null) { // Server Stats
            actor.commandEvent().replyEmbeds(InvisibleEmbed {
                title = "Server counting statistics"
                description = """
                        - Last Count: ${CountingManager.currentCount}
                        - Total Counts: ${CountingManager.serverTotalCounts}
                        - Highest Count: ${CountingManager.topServerCount}
                    """.trimIndent()

                field {
                    name = "Top 5 counters"
                    value = CountingManager.getTop5().joinToString("") { profile ->
                        "\n- <@${profile.id}>: ${profile.totalCounts}"
                    }
                }

            }).setEphemeral(true).queue()
        } else {
            val profile = target.getProfile()

            actor.commandEvent().replyEmbeds(InvisibleEmbed {
                title = "${target.name}'s counting statistics"
                description = """
                        - Total Counts: ${profile?.totalCounts}
                        - Highest Count: ${profile?.highestCount}
                        - Mistakes: ${profile?.countingFuckUps}
                    """.trimIndent()
            })
        }
    }
}