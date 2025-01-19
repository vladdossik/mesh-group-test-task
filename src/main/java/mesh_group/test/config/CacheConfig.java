package mesh_group.test.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${cache.expireAfterAccessMs}")
    private long expireAfterAccessMs;

    @Value("${cache.maximumSize}")
    private long maximumSize;

    @Value("${cache.recordStats}")
    private boolean recordStats;

    @Value("${cache.initialCapacity}")
    private int initialCapacity;

    @Value("${cache.weakKeys}")
    private boolean weakKeys;

    @Value("${cache.weakValues}")
    private boolean weakValues;

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        Caffeine<Object, Object> caffeineBuilder = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofMillis(expireAfterAccessMs))
                .maximumSize(maximumSize)
                .initialCapacity(initialCapacity);

        if (recordStats) {
            caffeineBuilder.recordStats();
        }
        if (weakKeys) {
            caffeineBuilder.weakKeys();
        }
        if (weakValues) {
            caffeineBuilder.weakValues();
        }

        return caffeineBuilder;
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                "users",
                "accounts",
                "usersFiltered"
        );
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
