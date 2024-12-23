package edu.jl.springrediscache;

import edu.jl.springrediscache.controller.CarController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.util.StopWatch;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableCaching
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class SpringRedisCacheApplication {
    private final CarController carController;

    @Autowired
    public SpringRedisCacheApplication(CarController carController) {
        this.carController = carController;
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringRedisCacheApplication.class, args);
    }

    @Bean
    ApplicationRunner runPerformanceTests() {
        return args -> {
            System.out.println("Starting the performance tests: ");
            StopWatch stopwatch = new StopWatch();

            // Test with cache support
            stopwatch.start();
            System.out.println(carController.findByIdWithCacheSupport(1L).getBody());
            System.out.println(carController.findByIdWithCacheSupport(1L).getBody());
            System.out.println(carController.findByIdWithCacheSupport(1L).getBody());
            stopwatch.stop();
            System.out.printf("Elapsed time for method calls WITH cache support: %s milliseconds.%n", stopwatch.getTotalTimeMillis());

            // Test without cache support
            stopwatch.start();
            System.out.println(carController.findByIdWithoutCacheSupport(1L).getBody());
            System.out.println(carController.findByIdWithoutCacheSupport(1L).getBody());
            System.out.println(carController.findByIdWithoutCacheSupport(1L).getBody());
            stopwatch.stop();
            System.out.printf("Elapsed time for method calls WITHOUT cache support: %s milliseconds.%n", stopwatch.getTotalTimeMillis());
        };
    }
}
