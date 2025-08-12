package github.loseex.logify.api.storage

import java.time.Duration

interface StorageAPI {
  /**
   * Stores a value with the specified key and optional expiration time.
   *
   * @param key The unique identifier for the value (non-null, non-empty)
   * @param value The data to be stored (non-null)
   * @param ttl Time-To-Live duration (use Duration.ZERO for no expiration)
   * @throws IllegalArgumentException if key or value is invalid
   */
  fun set(key: String, value: String, ttl: Duration): Unit

  /**
   * Retrieves the value associated with the key.
   *
   * @param key The key to look up
   * @return The stored value or null if key doesn't exist or has expired
   */
  fun get(key: String): String?

  /**
   * Permanently removes the key-value pair from storage.
   *
   * @param key The key to remove
   * @return No return value (success) or throws exception on critical failure
   */
  fun delete(key: String): Unit

  /**
   * Checks if a key exists in storage (regardless of TTL status).
   *
   * @param key The key to check
   * @return true if key exists (even if expired), false otherwise
   */
  fun exists(key: String): Boolean

  /**
   * Updates the expiration time for an existing key.
   *
   * @param key The key to modify
   * @param ttl New Time-To-Live duration
   * @throws NoSuchElementException if key doesn't exist
   */
  fun expire(key: String, ttl: Duration): Unit

  /**
   * Gets the remaining time before key expiration.
   *
   * @param key The key to check
   * @return Duration.ZERO if no TTL set, negative Duration if expired,
   *         or remaining time until expiration
   * @throws NoSuchElementException if key doesn't exist
   */
  fun ttl(key: String): Duration?
}