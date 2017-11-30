package com.qlqn.shiro;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.ehcache.EhCacheManager;

public class CacheManagerSession<K, V> implements Cache<K, V> {
	private EhCacheManager cacheManager;
	private Cache<K, V> cache = null;
	
	public Cache<K, V> getCache() {
		try {
			if(null == cache){
				cache = cacheManager.getCache("wx:ccx_antifraud_web:cache_sessionId");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cache;
	}
	@Override
	public void clear() throws CacheException {
		getCache().clear();
	}
	@Override
	public V get(K key) throws CacheException {
		return getCache().get(key);
	}
	@Override
	public Set<K> keys() {
		
		return getCache().keys();
	}
	@Override
	public V put(K key, V value) throws CacheException {
		return getCache().put(key, value);
	}
	@Override
	public V remove(K key) throws CacheException {
		return getCache().remove(key);
	}
	@Override
	public int size() {
		return getCache().size();
	}
	@Override
	public Collection<V> values() {
		return getCache().values();
	}
	public EhCacheManager getCacheManager() {
		return cacheManager;
	}
	public void setCacheManager(EhCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	public void setCache(Cache<K, V> cache) {
		this.cache = cache;
	}
}
