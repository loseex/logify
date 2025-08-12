package github.loseex.logify

import github.loseex.logify.api.storage.StorageAPI
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock

class Storage : StorageAPI {
  private val store = ConcurrentHashMap<String, String>()
  private val expired = ConcurrentHashMap<String, Long>()

  private val cleanupLock = ReentrantLock()
  private val cleanupCounter = AtomicInteger(0)

  companion object {
    private const val CLEANUP_THRESHOLD = 100
    private const val CLEANUP_BATCH_SIZE = 50
  }

  override fun set(key: String, value: String, ttl: Duration) {
    if (ttl.isNegative) throw RuntimeException("TTL must be non-negative")
    val expiration = convert(ttl)

    this.store[key] = value
    this.expired[key] = expiration

    scheduleCleanupIfNeeded()
  }

  override fun get(key: String): String? {
    if (isExpired(key)) {
      delete(key)
      return null
    }
    return store[key]
  }

  override fun delete(key: String) {
    store.remove(key)
    expired.remove(key)
  }

  override fun exists(key: String): Boolean {
    if (isExpired(key)) {
      delete(key)
      return false
    }
    return store.containsKey(key)
  }

  override fun expire(key: String, ttl: Duration) {
    if (!this.store.containsKey(key)) return
    this.expired[key] = this.convert(ttl)
    scheduleCleanupIfNeeded()
  }

  override fun ttl(key: String): Duration? {
    val expiration = this.expired[key] ?: return null
    val currentTime = System.currentTimeMillis()
    if (currentTime >= expiration) {
      this.delete(key)
      return null
    }
    return Duration.ofMillis(expiration - currentTime)
  }

  override fun isExpired(key: String, time: Long): Boolean {
    val expiration = this.expired[key] ?: return true
    return time >= expiration
  }

  private fun scheduleCleanupIfNeeded() {
    if (this.cleanupCounter.incrementAndGet() >= CLEANUP_THRESHOLD) {
      performCleanup()
    }
  }

  private fun convert(ttl: Duration): Long {
    return System.currentTimeMillis() + ttl.toMillis()
  }

  private fun performCleanup() {
    if (!this.cleanupLock.tryLock()) return

    try {
      this.cleanupCounter.set(0)
      val currentTime = System.currentTimeMillis()
      var count = 0

      val iterator = this.expired.entries.iterator()
      while (iterator.hasNext() && count < CLEANUP_BATCH_SIZE) {
        val (key, expiration) = iterator.next()
        if (expiration <= currentTime) {
          iterator.remove()
          this.store.remove(key)
          count++
        }
      }
    } finally {
      this.cleanupLock.unlock()
    }
  }

  private val Duration.isNegative: Boolean
    get() = this < Duration.ZERO
}