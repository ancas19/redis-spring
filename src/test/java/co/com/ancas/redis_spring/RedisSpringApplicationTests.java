package co.com.ancas.redis_spring;

import org.junit.jupiter.api.RepeatedTest;
import org.redisson.api.RAtomicLongReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class RedisSpringApplicationTests {

    @Autowired
    private ReactiveStringRedisTemplate template;
    @Autowired
    private RedissonReactiveClient redissonReactiveClient;

    @RepeatedTest(3)
    void springDataRedisTest() {
        ReactiveValueOperations<String, String> valueOperations = this.template.opsForValue();

        Long before = System.currentTimeMillis();

        Mono<Void> mono = Flux.range(1, 500)
                .map(i -> valueOperations.increment("user:1:visit"))
                .then();

        StepVerifier.create(mono)
                .verifyComplete();
        Long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before) + "ms");
    }

    @RepeatedTest(3)
    void redissonTest() {
        RAtomicLongReactive atomicLong = this.redissonReactiveClient.getAtomicLong("user:2:visit");
        Long before = System.currentTimeMillis();
        Mono<Void> mono = Flux.range(1, 500)
                .map(i -> atomicLong.incrementAndGet())
                .then();
        StepVerifier.create(mono)
                .verifyComplete();
        Long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before) + "ms");
    }

}
