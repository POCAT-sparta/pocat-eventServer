package sparta.eventserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EventServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventServerApplication.class, args);
    }

}
