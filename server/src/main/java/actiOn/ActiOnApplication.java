package actiOn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableJpaAuditing
@SpringBootApplication
public class ActiOnApplication {
	public static void main(String[] args) {
		SpringApplication.run(ActiOnApplication.class, args);
	}
}