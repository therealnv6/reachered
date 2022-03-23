package io.github.devrawr.reach.commands

import io.github.devrawr.reach.cheat.type.ReachCheat
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object CheatCommand : CommandExecutor
{
    private val options = hashMapOf<String, (Player) -> Unit>(
        "reach" to {
            ReachCheat.toggle(it)
        },
        "aimbot" to {
            it.sendMessage("${ChatColor.RED}Unsupported")
        }
    )

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean
    {
        if (args.size < 2)
        {
            sender.sendMessage("${ChatColor.RED}/${label} <player> <cheat>")
            return true
        }

        val player = Bukkit.getPlayer(args[0])
        val type = args[1]

        val option = options[type]

        if (player != null && option != null)
        {
            option.invoke(player)
        } else
        {
            sender.sendMessage("${ChatColor.RED}Options: ${options.keys.joinToString(", ") { it }}")
        }

        return true
    }
}