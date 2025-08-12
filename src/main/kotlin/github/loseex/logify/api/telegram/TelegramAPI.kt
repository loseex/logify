package github.loseex.logify.api.telegram

interface TelegramAPI {
  /**
   * Initializes and starts the Telegram bot instance.
   *
   * This method should:
   * - Load bot configuration from plugin settings
   * - Authenticate with Telegram using the bot token
   * - Register command handlers for Minecraft-related actions
   * - Set up webhook or long-polling connection
   * - Begin listening for Telegram messages
   *
   * @throws PluginConfigurationException if bot token or settings are invalid
   * @throws TelegramConnectionException if connection to Telegram servers fails
   */
  fun inject(): Unit

  /**
   * Safely stops the Telegram bot integration.
   *
   * This method should:
   * - Disconnect from Telegram servers gracefully
   * - Save any pending notifications or logs
   * - Release resources to prevent memory leaks
   * - Unregister all Minecraft event listeners
   *
   * Designed to be called during plugin disable/reload.
   * Multiple calls should be handled safely.
   */
  fun shutdown(): Unit
}