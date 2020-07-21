package restfull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import restfull.config.WebConfig;

/**
 * RestFullApp.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/8/2020
 */
@SpringBootApplication
public class RestFullApp extends SpringBootServletInitializer {
    public static void main(final String[] args) {
        SpringApplication.run(RestFullApp.class, args);
    }

    @Override
    protected final SpringApplicationBuilder configure(
            final SpringApplicationBuilder application) {
        return application.sources(WebConfig.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
