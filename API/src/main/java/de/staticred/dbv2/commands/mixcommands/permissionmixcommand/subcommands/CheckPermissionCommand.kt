package de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands

import de.staticred.dbv2.DBUtil
import de.staticred.dbv2.discord.util.BotHelper
import de.staticred.dbv2.discord.util.RoleBuilder
import de.staticred.dbv2.player.CommandSender
import de.staticred.dbv2.player.DBUPlayer
import net.dv8tion.jda.api.entities.Role

class CheckPermissionCommand {
    fun getName(): String {
        return "Check"
    }

    fun getPermission(): String {
        return "db.cmd.mix.dbperms.check"
    }

    fun execute(player: CommandSender, args: Array<out String>) {
        if (!player.hasPermission(getPermission())) {
            player.sendMessage("You don't have permission for this action!")
            return
        }

        if (args.size < 0) {
            player.sendMessage("Use: dbperms check <player/role> <permission>")
            return
        }
        var permission = ""

        for (i in 2 until args.size) {
            permission = permission.plus(args[i])
        }

        val playerOrRole = args[1]

        var hasPermission = false

        val target:DBUPlayer? = DBUtil.getINSTANCE().proxy.getOnlinePlayer(playerOrRole)

        val role:Role? = RoleBuilder.buildRoleFromMessage(playerOrRole)

        if (target == null && role == null) {
            player.sendMessage("Target not found on network")
            return
        }
        else if (target != null)
            hasPermission = target.hasPermission(permission)


        if (role == null) {
            player.sendMessage("Invalid role!")
            return
        }
        else
            hasPermission = DBUtil.getINSTANCE().permissionHandler!!.hasPermission(role.idLong, permission)

        println(DBUtil.getINSTANCE().permissionHandler!!.hasPermission(role.idLong, permission))

        player.sendMessage("Role or player: $playerOrRole has permission for $permission set to $hasPermission")
    }


}