package co.com.ancas.redis_spring.fib.controller;

import co.com.ancas.redis_spring.fib.service.FibService;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fib")
public class FibController {
    private final FibService fibService;

    public FibController(FibService fibService) {
        this.fibService = fibService;
    }


    @GetMapping("/{index}/{name}")
    public Mono<Integer> getFib(@PathVariable("index") int index, @PathVariable("name") String name) {
        return Mono.fromSupplier(()->this.fibService.getFib(index,name));
    }

    @GetMapping("/evict/{index}")
    public Mono<Void> evictCache(@PathVariable("index") int index) {
        return Mono.fromRunnable(() -> this.fibService.evictCache(index));
    }
}
