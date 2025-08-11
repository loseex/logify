package github.loseex.logify

import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin() {

  companion object {
    private lateinit var instance: Plugin

    @JvmStatic
    fun getInstance(): Plugin = instance
  }

  override fun onEnable() {
    instance = this
  }

  override fun onDisable() {
  }
}
