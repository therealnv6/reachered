package io.github.devrawr.reach.util

import org.bukkit.entity.Entity
import org.bukkit.entity.Player

object EntityUtil
{
    inline fun <reified T : Entity> getClosestEntityToCrosshair(player: Player, range: Double): T?
    {
        val direction = player.eyeLocation.direction
        val entities = player.getNearbyEntities(
            range, range, range
        )

        for (entity in entities)
        {
            if (entity !is T)
            {
                continue
            }

            val angle = entity.location
                .subtract(player.location)
                .toVector()
                .normalize()

            if (direction.distance(angle) < 0.1)
            {
                return entity
            }
        }

        return null
    }
}