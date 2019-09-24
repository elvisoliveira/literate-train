package br.com.elvisoliveira.literatetrain;

import br.com.elvisoliveira.literatetrain.Cache.SingleDigitCache;
import br.com.elvisoliveira.literatetrain.Cache.UserCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LiterateTrainApp {
    public static void main(String[] args) {
        SpringApplication.run(LiterateTrainApp.class, args);
    }

    // Singletons
    @Bean(name = "UserCache")
    public UserCache userCache() {
        return new UserCache();
    }

    @Bean(name = "SingleDigitCache")
    public SingleDigitCache singleDigitCache() {
        return new SingleDigitCache();
    }
}