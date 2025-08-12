package github.loseex.logify

import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin() {

  companion object {
    public lateinit var instance: Plugin
      private set
    public lateinit var storage: Storage
      private set
  }

  override fun onEnable() {
    instance = this
    storage = Storage()

    this.saveDefaultConfig()
  }

  override fun onDisable() {
  }
}
