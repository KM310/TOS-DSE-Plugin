import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Main : JavaPlugin(), Listener {

    private lateinit var acceptedFile: File
    private lateinit var acceptedConfig: YamlConfiguration

    override fun onEnable() {
        if (!dataFolder.exists()) dataFolder.mkdirs()

        // YAML Datei laden
        acceptedFile = File(dataFolder, "accepted.yml")
        if (!acceptedFile.exists()) {
            acceptedFile.createNewFile()
        }
        acceptedConfig = YamlConfiguration.loadConfiguration(acceptedFile)

        server.pluginManager.registerEvents(this, this)

        // Commands
        getCommand("showTOS")?.setExecutor { sender, _, _, _ ->
            if (sender is Player) showTOS(sender)
            true
        }

        getCommand("showDSE")?.setExecutor { sender, _, _, _ ->
            if (sender is Player) showDSE(sender)
            true
        }

        getCommand("acceptTOS")?.setExecutor { sender, _, _, _ ->
            if (sender is Player) acceptTOS(sender)
            true
        }

        getCommand("acceptDSE")?.setExecutor { sender, _, _, _ ->
            if (sender is Player) acceptDSE(sender)
            true
        }

        getCommand("declineTOS")?.setExecutor { sender, _, _, _ ->
            if (sender is Player) declineTOS(sender)
            true
        }

        getCommand("declineDSE")?.setExecutor { sender, _, _, _ ->
            if (sender is Player) declineDSE(sender)
            true
        }

        logger.info("Plugin aktiviert.")
    }

    override fun onDisable() {
        acceptedConfig.save(acceptedFile)
        logger.info("Plugin deaktiviert.")
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val p = e.player

        val tosAccepted = acceptedConfig.getBoolean("${p.uniqueId}.tos", false)
        val dseAccepted = acceptedConfig.getBoolean("${p.uniqueId}.dse", false)

        if (!tosAccepted) showTOS(p)
        if (!dseAccepted) showDSE(p)
    }

    private fun saveStatus(p: Player, key: String, value: Boolean) {
        acceptedConfig.set("${p.uniqueId}.$key", value)
        acceptedConfig.save(acceptedFile)
    }

    private fun loadFile(name: String): List<String> {
        val file = File(dataFolder, name)

        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.writeText("Die Datei $name wurde automatisch erstellt.")
        }

        return file.readLines()
    }

    fun showTOS(p: Player) {
        p.sendMessage(Component.text("=== TOS ===", NamedTextColor.GOLD))

        val lines = loadFile("tos.txt")
        lines.forEach {
            p.sendMessage(Component.text(it, NamedTextColor.YELLOW))
        }

        p.sendMessage(Component.text("Nutze /acceptTOS zum Akzeptieren.", NamedTextColor.GREEN))
    }

    fun showDSE(p: Player) {
        p.sendMessage(Component.text("=== DSE ===", NamedTextColor.GOLD))

        val lines = loadFile("dse.txt")
        lines.forEach {
            p.sendMessage(Component.text(it, NamedTextColor.YELLOW))
        }

        p.sendMessage(Component.text("Nutze /acceptDSE zum Akzeptieren.", NamedTextColor.GREEN))
    }

    fun acceptTOS(p: Player) {
        saveStatus(p, "tos", true)
        p.sendMessage(Component.text("Du hast die TOS akzeptiert!", NamedTextColor.GREEN))
    }

    fun acceptDSE(p: Player) {
        saveStatus(p, "dse", true)
        p.sendMessage(Component.text("Du hast die DSE akzeptiert!", NamedTextColor.GREEN))
    }

    fun declineTOS(p: Player) {
        p.kick(Component.text("Du hast die TOS abgelehnt.", NamedTextColor.RED))
    }

    fun declineDSE(p: Player) {
        p.kick(Component.text("Du hast die DSE abgelehnt.", NamedTextColor.RED))
    }
}
