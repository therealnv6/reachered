package io.github.devrawr.reach.util

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

object DamageUtil
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

    fun calculateDamage(player: Player, entity: Entity): Double
    {
        val item = player.itemInHand
        var damage = 0.0

        if (item != null && damageMap.containsKey(item.type))
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

        return damage
    }
}