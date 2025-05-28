package co.com.ancas.redis_spring.geo.controller;

import co.com.ancas.redis_spring.geo.dto.Restaurant;
import co.com.ancas.redis_spring.geo.service.RestaurantLocatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/geo")
public class RestaurantController {
    private final RestaurantLocatorService locatorService;

    public RestaurantController(RestaurantLocatorService locatorService) {
        this.locatorService = locatorService;
    }

    @GetMapping("/{zip}")
    public Flux<Restaurant> getRestaurantsByZip(@PathVariable("zip") String zip) {
        return locatorService.getRestaurants(zip);
    }
}
