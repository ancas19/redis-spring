package co.com.ancas.redis_spring.city.service;

import co.com.ancas.redis_spring.city.client.CityClient;
import co.com.ancas.redis_spring.city.dto.City;
import org.redisson.api.RMapCache;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CityService {


    private RMapReactive<String, City> cityMap;
    private final CityClient cityClient;

    public CityService(RedissonReactiveClient client, CityClient cityClient) {
        this.cityMap = client.getMapCache("city", new TypedJsonJacksonCodec(String.class, City.class));
        this.cityClient = cityClient;
    }

    /*
        get from cache
         if empty getFrom db/ source
                 put in the cache

       return
     */
    public Mono<City> getCityInfo(final String zip) {
        return cityMap.get(zip)
                .onErrorResume(ex->this.cityClient.getCityInfo(zip));
    }

    @Scheduled(fixedRate = 10_000)
    public  void updateCityInformation(){
        System.out.println("Updating city information");
        this.cityClient.getAll()
                .collectList()
                .map(list ->list.stream().collect(Collectors.toMap(City::getZip, Function.identity())))
                .flatMap(this.cityMap::putAll)
                .subscribe();
    }
}
