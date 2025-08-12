package github.loseex.logify

import github.loseex.logify.api.storage.StorageAPI
import github.loseex.logify.api.telegram.TelegramAPI
import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin() {

  companion object {
    public lateinit var instance: Plugin
      private set
    public lateinit var storage: StorageAPI
      private set
    public lateinit var telegram: TelegramAPI
      private set
  }

  override fun onEnable() {
    instance = this
    storage = Storage()
    telegram = Telegram()

    this.saveDefaultConfig()

    telegram.inject()
  }

  override fun onDisable() {
    telegram.shutdown()
  }

  inline fun <reified T> getConfigVariable(path: String): T? {
    return config.get(path)?.let { value ->
      if (T::class.isInstance(value)) value as T else null
    }
  }
}
