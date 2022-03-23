package io.github.devrawr.reach.commands

import io.github.devrawr.reach.ReachPlugin
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object ReachCommand : CommandExecutor
{
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean
    {
        if (args.size < 2)
        {
            sender.sendMessage("${ChatColor.RED}/${label} <player> <reach>")
            return true
        }

        val player = Bukkit.getPlayer(args[0])
        val range = args[1].toDoubleOrNull()

        if (player == null)
        {
            sender.sendMessage("${ChatColor.RED}No player found by name ${args[0]}")
            return true
        }

        if (range == null)
        {
            sender.sendMessage("${ChatColor.RED}${args[1]} is not a double, format: x.x")
            return true
        }

        ReachPlugin.ranges[player.uniqueId] = range

        return true
    }
}