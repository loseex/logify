package github.loseex.logify

import github.loseex.logify.api.storage.StorageAPI
import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin() {

  companion object {
    public lateinit var instance: Plugin
      private set
    public lateinit var storage: StorageAPI
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
