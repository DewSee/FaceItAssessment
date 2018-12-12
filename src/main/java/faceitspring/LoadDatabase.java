package faceitspring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new User("John", "Doe", "DoeDoe", "password", "johndoe00@gmail.com", "United Kingdom")));
            log.info("Preloading " + repository.save(new User("Jane", "Doe", "JaneJane", "password", "janedoe00@gmail.com", "United Kingdom")));
        };
    }
}
