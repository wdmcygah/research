package com.research;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

public class EhcacheWrapper<K, V> implements CacheWrapper<K, V> 
{
    private final String cacheName;
    private final CacheManager cacheManager;

    public EhcacheWrapper(final String cacheName, final CacheManager cacheManager)
    {
        this.cacheName = cacheName;
        this.cacheManager = cacheManager;
    }

    public void put(final K key, final V value)
    {
        getCache().put(new Element(key, value));
    }

    public V get(final K key) 
    {
        Element element = getCache().get(key);
        if (element != null) {
            return (V) element.getValue();
        }
        return null;
    }

    public Ehcache getCache() 
    {
        return cacheManager.getCache(cacheName);
    }
    
    public static void main(String[] args) {
    	CacheManager manager = new CacheManager();
    	Ehcache cache = manager.getCache("sampleCache1");
    	CacheConfiguration config = cache.getCacheConfiguration();
    	config.setTimeToIdleSeconds(60);
    	config.setTimeToLiveSeconds(120);
    	String key = "myKey";
    	Element value = cache.get(key);
    	//System.out.println("before set...value="+value.getValue());
    	Element element = new Element(key,"myvalue");
    	cache.put(element);
    	value = cache.get(key);
    	System.out.println("after set...value="+value.getValue());
	}
    
}