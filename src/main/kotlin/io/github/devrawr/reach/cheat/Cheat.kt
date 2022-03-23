package io.github.devrawr.reach.cheat

import io.github.devrawr.events.Events
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.player.PlayerEvent
import java.util.UUID

abstract class Cheat<T : Event>
{
    abstract val event: Class<T>
    private val toggled = mutableListOf<UUID>()

    abstract fun handle(event: T)

    fun handleCasted(event: Event)
    {
        this.handle(event as T)
    }

    /**
     * Toggle the [Cheat] for a specific [Player]
     *
     * Adds the player's [UUID] to
     * the [Cheat.toggled] list.
     *
     * @param player the player to toggle the cheat for
     */
    fun toggle(player: Player)
    {
        if (toggled.contains(player.uniqueId))
        {
            toggled -= player.uniqueId
        } else
        {
            toggled += player.uniqueId
        }
    }

    /**
     * Check if a player has the current [Cheat] toggled.
     *
     * Check if the [Cheat.toggled] list contains
     * the provided [Player]'s [UUID].
     *
     * @param player the player to get the unique id from
     * @return whether the cheat is toggled.
     */
    fun isToggled(player: Player): Boolean
    {
        return this.toggled.contains(player.uniqueId)
    }
}