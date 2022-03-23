package io.github.devrawr.reach.cheat.type

import io.github.devrawr.reach.cheat.Cheat
import io.github.devrawr.reach.util.DamageUtil
import io.github.devrawr.reach.util.EntityUtil
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import java.util.UUID

object ReachCheat : Cheat<PlayerInteractEvent>()
{
    override val event = PlayerInteractEvent::class.java
    val rangeMap = hashMapOf<UUID, Double>()

    override fun handle(event: PlayerInteractEvent)
    {
        val player = event.player
        val range = rangeMap[player.uniqueId]

        if (range != null)
        {
            val target = EntityUtil.getClosestEntityToCrosshair<Player>(player, range)

            if (target != null)
            {
                val damage = DamageUtil.calculateDamage(
                    player, target
                )

                target.damage(damage, player)
            }
        }
    }
}