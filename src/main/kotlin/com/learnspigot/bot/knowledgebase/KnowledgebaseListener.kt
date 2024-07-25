package com.learnspigot.bot.knowledgebase

import com.learnspigot.bot.Environment
import gg.flyte.neptune.annotation.Inject
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.entities.channel.unions.ChannelUnion
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent
import net.dv8tion.jda.api.events.channel.update.ChannelUpdateArchivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class KnowledgebaseListener : ListenerAdapter() {

    @Inject
    private lateinit var knowledgebasePostRegistry: KnowledgebasePostRegistry

    private val ChannelUnion.isInKnowledgebase: Boolean get() = type == ChannelType.GUILD_PUBLIC_THREAD
            && asThreadChannel().parentChannel.id == Environment.KNOWLEDGEBASE_CHANNEL_ID

    override fun onChannelCreate(e: ChannelCreateEvent) {
        if (!e.channel.isInKnowledgebase) return
        knowledgebasePostRegistry.posts[e.channel.name] = e.channel.id
    }

    override fun onChannelDelete(e: ChannelDeleteEvent) {
        if (!e.channel.isInKnowledgebase) return
        knowledgebasePostRegistry.posts.remove(e.channel.name)
    }

    override fun onChannelUpdateArchived(e: ChannelUpdateArchivedEvent) {
        if (e.channelType != ChannelType.GUILD_PUBLIC_THREAD) return
        val channel = e.channel.asThreadChannel()
        if (channel.parentChannel.id != Environment.KNOWLEDGEBASE_CHANNEL_ID && channel.parentChannel.id != Environment.PROJECTS_CHANNEL_ID) return

        if (channel.isArchived){
            channel.manager.setArchived(false).setLocked(false).queue()
        }
    }

}