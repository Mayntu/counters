package test.group.counters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import test.group.counters.core.Database;
import test.group.counters.dao.CounterDAO;
import test.group.counters.repositories.UserRepository;

@Configuration
public class WebSpringConfig implements WebMvcConfigurer
{
    @Autowired
    private UserRepository userRepository;

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

    @Bean
    public UserDetailsService userDetailsService()
    {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Разрешите источники, откуда разрешены запросы
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
