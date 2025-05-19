package co.com.ancas.redis_spring.weather.controller;

import co.com.ancas.redis_spring.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;


    @GetMapping("/{zipCode}")
    public Mono<Integer> getWeatherInfo(@PathVariable("zipCode") int zipCode) {
        return Mono.fromSupplier(()->this.weatherService.getInfo(zipCode));
    }
}
