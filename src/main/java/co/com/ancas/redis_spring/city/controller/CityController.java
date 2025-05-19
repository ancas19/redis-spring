package co.com.ancas.redis_spring.city.controller;

import co.com.ancas.redis_spring.city.dto.City;
import co.com.ancas.redis_spring.city.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;

    @GetMapping("/{zip}")
    public Mono<City> getCityInfo(@PathVariable("zip") String zip) {
        return this.cityService.getCityInfo(zip);
    }
}
