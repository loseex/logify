package github.loseex.logify.api.storage

/**
 * Centralized registry of all storage keys used in the application.
 *
 * This object serves as a catalog of all predefined keys for accessing data
 * in the storage HashMap. Using these constants ensures:
 * - Type-safe key references throughout the application
 * - Prevention of key collisions
 * - Consistent key naming conventions
 * - Easy maintenance of all storage keys in one place
 *
 * Usage example:
 * ```
 * Plugin.storage.get(Keys.SESSION + event.player.name)
 * ```
 */
object Keys {
}