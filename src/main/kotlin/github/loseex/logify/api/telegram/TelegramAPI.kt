package github.loseex.logify.api.telegram

interface TelegramAPI {
  /**
   * Initializes and starts the Telegram bot with long-polling.
   *
   * This asynchronous method:
   * - Retrieves bot token from plugin configuration
   * - Initializes Telegram bot API client
   * - Starts long-polling connection in background
   * - Sets up behaviour context for message handling
   *
   * Runs in coroutine scope with IO dispatcher.
   *
   * @throws RuntimeException if bot token is not configured
   * @throws Exception for any Telegram API connection errors
   */
  fun inject(): Unit

  /**
   * Gracefully shuts down the Telegram bot connection.
   *
   * This method:
   * - Closes the behaviour context
   * - Cancels the polling job
   * - Releases Telegram API resources
   * - Handles multiple calls safely (idempotent)
   *
   * Logs completion status or any errors encountered.
   */
  fun shutdown(): Unit
}