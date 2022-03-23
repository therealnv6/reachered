package io.github.devrawr.reach

import io.github.devrawr.events.Events
import io.github.devrawr.reach.commands.ReachCommand
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class ReachPlugin : JavaPlugin()
{
    companion object
    {
        val ranges = hashMapOf<UUID, Double>()
    }

    override fun onEnable()
    {
        this.getCommand("reach").executor = ReachCommand

        Events
            .listenTo<PlayerInteractEvent>()
            .filter {
                it.action == Action.LEFT_CLICK_AIR && ranges.containsKey(it.player.uniqueId)
            }
            .on {
                val player = it.player
                val reach = ranges[player.uniqueId]!!

                val entities = player.getNearbyEntities(reach, reach, reach)
                val direction = player.eyeLocation.direction

                var damage = 0.0 // TODO: 3/23/22 get actual damage of current item

                for (entity in entities)
                {
                    if (entity !is Player)
                    {
                        continue
                    }

                    val angle = entity.location.subtract(player.location).toVector().normalize()

                    if (direction.distance(angle) < 0.1)
                    {
                        entity.damage(damage, player)
                    }
                }
            }
    }
}