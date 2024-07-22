package test.group.counters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import test.group.counters.core.Database;
import test.group.counters.dao.CounterDAO;

@Configuration
public class WebSpringConfig implements WebMvcConfigurer {
    @Bean
    public Database database()
    {
        return new Database();
    }

//    @Bean
    public CounterDAO counterDAO()
    {
        return new CounterDAO();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Разрешите источники, откуда разрешены запросы
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
