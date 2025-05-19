package co.com.ancas.redis_spring.city.client;

import co.com.ancas.redis_spring.city.dto.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CityClient {

    private final WebClient webClient;

    public CityClient(@Value("${city.service.url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }


    public Mono<City> getCityInfo(String zip) {
        return this.webClient.get()
                .uri("/open-city-api/{zip}", zip)
                .retrieve()
                .bodyToMono(City.class);
    }
}
