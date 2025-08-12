package github.loseex.logify

import dev.inmo.tgbotapi.bot.RequestsExecutor
import dev.inmo.tgbotapi.extensions.api.send.reply
import dev.inmo.tgbotapi.extensions.api.telegramBot
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.extensions.behaviour_builder.buildBehaviourWithLongPolling
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onCommand
import github.loseex.logify.api.telegram.TelegramAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class Telegram : TelegramAPI {
  private lateinit var application: RequestsExecutor
  private final val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
  private final var job: Job? = null
  private final var context: BehaviourContext? = null

  private lateinit var secret: String

  override fun inject() {
    this.scope.launch {
      try {
        this@Telegram.bootstrap()
      } catch (e: Exception) {
        Plugin.instance.logger.severe("Failed to initialize Telegram bot: ${e.message}")
      }
    }
  }

  override fun shutdown() {
    try {
      this.context?.close()
      this.job?.cancel()
      Plugin.instance.logger.info("Telegram bot shutdown completed")
    } catch (e: Exception) {
      Plugin.instance.logger.severe("Error during Telegram bot shutdown: ${e.message}")
    }
  }

  private suspend fun bootstrap() {
    this.retrieveSecret()
    this.initialize(this.secret)



    this.job = this.application.buildBehaviourWithLongPolling(CoroutineScope(Dispatchers.Default)) {
      this@Telegram.context = this

      Plugin.instance.logger.info("Telegram bot started polling successfully")
    }
  }

  private fun initialize(secret: String) {
    this.application = telegramBot(secret)
  }

  private fun retrieveSecret() {
    this.secret = Plugin.instance.getConfigVariable<String>("telegram.secret")
      ?: throw RuntimeException("Telegram bot token is not configured")
  }
}