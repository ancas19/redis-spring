package co.com.ancas.redis_spring.fib.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FibService {

    // have a strategy for cache evict
    @Cacheable(value = "math:fib",key = "#index")
    public int getFib(int index, String name) {
        System.out.println("Calculating fib for index: " + index + ", name: " + name);
        return this.fib(index);
    }

    @CacheEvict(value = "math:fib", key = "#index")
    public void evictCache(int index) {
        System.out.println("Evicting cache for index: " + index);
    }

    @Scheduled(fixedRate = 10_000)
    @CacheEvict(value = "math:fib", allEntries = true)
    public void evictAllCache() {
        System.out.println("Evicting all cache");
    }


    // intentional 2 N
    private int fib(int index) {
        if (index < 2) {
            return index;
        }
        return fib(index - 1) + fib(index - 2);
    }

}
