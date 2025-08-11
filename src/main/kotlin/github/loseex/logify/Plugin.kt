package github.loseex.logify

import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin() {

  companion object {
    public lateinit var instance: Plugin
      private set
  }

  override fun onEnable() {
    instance = this

    this.saveDefaultConfig()
  }

  override fun onDisable() {
  }
}
