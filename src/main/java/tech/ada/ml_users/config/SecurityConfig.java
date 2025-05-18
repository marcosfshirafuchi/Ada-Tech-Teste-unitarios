//package tech.ada.ml_users.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(req -> {
//                    req.requestMatchers(HttpMethod.POST, "/usuarios")
//                            .permitAll();
//                    req.requestMatchers(HttpMethod.GET, "/usuarios/**")
//                            .authenticated();
//                    req.requestMatchers(HttpMethod.PUT, "/usuarios")
//                            .authenticated();
//                    req.requestMatchers(new AntPathRequestMatcher("/h2/**"))
//                            .permitAll();
//                    req.requestMatchers(new AntPathRequestMatcher("/favicon.ico"))
//                            .permitAll();
//                    req.requestMatchers(new AntPathRequestMatcher("/swagger.**"))
//                            .permitAll();
//                    req.requestMatchers(new AntPathRequestMatcher("/swagger-ui/**"))
//                            .permitAll();
//                    req.requestMatchers(new AntPathRequestMatcher("/swagger-config/**"))
//                            .permitAll();
//                    req.requestMatchers(new AntPathRequestMatcher("/v3/**"))
//                            .permitAll();
//
//                })
//                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
//                .build();
//    }
//
//    @Bean
//    public AuthenticationProvider authProvider(UserDetailsService userDetailsService) {
//        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
//        daoAuthProvider.setUserDetailsService(userDetailsService);
//        daoAuthProvider.setPasswordEncoder(passwordEncoder());
//        return daoAuthProvider;
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}