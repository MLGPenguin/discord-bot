package com.learnspigot.bot.intellijkey

import com.learnspigot.bot.util.replyEphemeral
import gg.flyte.neptune.annotation.Command
import gg.flyte.neptune.annotation.Inject
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class KeysLeftCommand {

    @Inject
    private lateinit var keyRegistry: IJUltimateKeyRegistry

    @Command(
        name = "keysleft",
        description = "View amount of IntelliJ Ultimate keys remaining",
        permissions = [Permission.MANAGE_ROLES]
    )
    fun onKeysLeftCommand(event: SlashCommandInteractionEvent) {
        val amount = keyRegistry.keys.size
        event.replyEphemeral("There are **$amount** IntelliJ Ultimate keys left in this batch." + if (amount < 100) " Maybe time to restock?" else "")
    }

}