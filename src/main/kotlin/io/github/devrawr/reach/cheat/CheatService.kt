package io.github.devrawr.reach.cheat

import io.github.devrawr.events.Events
import io.github.devrawr.reach.cheat.type.ReachCheat
import org.bukkit.event.player.PlayerEvent

object CheatService
{
    val cheats = mutableListOf<Cheat<*>>()

    fun initialize()
    {
        register(ReachCheat)
    }

    fun register(cheat: Cheat<*>)
    {
        this.cheats.add(cheat)

        Events
            .listenTo(cheat.event)
            .filter {
                (it is PlayerEvent && cheat.isToggled(it.player)) || it !is PlayerEvent
            }
            .on {
                cheat.handleCasted(it)
            }
    }
}