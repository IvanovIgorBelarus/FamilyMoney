package by.itacademy.familywallet.core.repository

interface CacheRepository<T> {
    fun put(cache: T)
    fun get(): T
    fun clear()
    fun isEmpty(): Boolean
}