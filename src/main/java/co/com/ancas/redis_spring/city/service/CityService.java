package co.com.ancas.redis_spring.city.service;

import co.com.ancas.redis_spring.city.client.CityClient;
import co.com.ancas.redis_spring.city.dto.City;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CityService {


    private RMapReactive<String, City> cityMap;
    private final CityClient cityClient;

    public CityService(RedissonReactiveClient client, CityClient cityClient) {
        this.cityMap = client.getMap("city", new TypedJsonJacksonCodec(String.class, City.class));
        this.cityClient = cityClient;
    }

    /*
        get from cache
         if empty getFrom db/ source
                 put in the cache

       return
     */
    public Mono<City> getCityInfo(String zip) {
        return cityMap.get(zip)
                .switchIfEmpty(
                        this.cityClient.getCityInfo(zip)
                                .flatMap(data -> cityMap.fastPut(zip, data)
                                        .thenReturn(data)
                                )
                );
    }

}
