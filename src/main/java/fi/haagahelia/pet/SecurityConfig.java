package fi.haagahelia.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fi.haagahelia.pet.web.UserDetailServiceImpl;

/**
 * This class configures the security settings for the application.
 * It sets up authentication and authorization rules, specifies the login and
 * logout behavior, and defines beans for password encoding and the security
 * filter chain.
 * The configuration ensures that certain endpoints are publicly accessible
 * while others require authentication.
 */

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    // Define the security chain bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/createUser", "/reset", "/resetPassword").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/expenses", true).permitAll())
                .logout(logout -> logout.permitAll());
        return http.build();
    }

    // Configure the global authentication settings, set user details service and
    // password encoder
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // Define the password encoder bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
