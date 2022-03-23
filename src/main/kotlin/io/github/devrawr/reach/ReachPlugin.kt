package io.github.devrawr.reach

import io.github.devrawr.events.Events
import io.github.devrawr.reach.commands.ReachCommand
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class ReachPlugin : JavaPlugin()
{
    // couldn't find any information on how to get the damage of an item
    // without using any NMS code, so I assume it straight up is not possible.
    // Wanted to keep this non-version dependent and I cannot be arsed to do NMS for such a useless project.
    // If you want to change this up, or add items, feel free to do so. I simply, cannot, be, arsed.
    private val damageMap = hashMapOf(
        Material.DIAMOND_SWORD to 7.0,
        Material.IRON_SWORD to 6.0,
        Material.WOOD_SWORD to 4.0,
        Material.STONE_SWORD to 5.0,
        Material.GOLD_SWORD to 4.0,
    )

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

                var damage = 0.0

                val item = player.itemInHand

                if (item != null && !damageMap.containsKey(item.type)) // TODO: 3/23/22 normalize by armor I guess
                {
                    damage += damageMap[item.type]!!

                    if (item.containsEnchantment(Enchantment.DAMAGE_ALL))
                    {
                        damage += (0.5 * item.getEnchantmentLevel(Enchantment.DAMAGE_ALL)) + 0.5
                    }
                } else
                {
                    damage = 0.5
                }

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