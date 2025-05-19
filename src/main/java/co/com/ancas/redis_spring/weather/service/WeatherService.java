package co.com.ancas.redis_spring.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final ExternalServiceClient externalServiceClient;

    @Cacheable(value = "weather")
    public int getInfo(int zipCode){
        return 0;
    }

    @Scheduled(fixedRate = 10000)
    public void updateWeather() {
        System.out.println("Updating weather info");
        IntStream.rangeClosed(1,5)
                .forEach(this.externalServiceClient::getWeatherInfo);
    }


}
