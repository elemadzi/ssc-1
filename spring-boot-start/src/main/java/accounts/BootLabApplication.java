package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import accounts.internal.JpaAccountManager;

@SpringBootApplication
public class BootLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootLabApplication.class, args);
    }
    
    @Bean
    public AccountManager accountManager(){
    return new JpaAccountManager();
    }

}
