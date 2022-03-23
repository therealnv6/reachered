package io.github.devrawr.reach

import io.github.devrawr.reach.cheat.CheatService
import io.github.devrawr.reach.commands.CheatCommand
import io.github.devrawr.reach.commands.ReachCommand
import org.bukkit.plugin.java.JavaPlugin

class ReachPlugin : JavaPlugin()
{
    override fun onEnable()
    {
        CheatService.initialize()

        this.getCommand("reach").executor = ReachCommand
        this.getCommand("cheat").executor = CheatCommand
    }
}