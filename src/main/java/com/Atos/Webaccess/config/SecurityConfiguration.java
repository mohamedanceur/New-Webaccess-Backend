package com.Atos.Webaccess.config;

import com.Atos.Webaccess.Domain.parametre.ParametreService;
import com.Atos.Webaccess.User.Services.HpasswordService;
import com.Atos.Webaccess.User.Services.UserService;
import com.Atos.Webaccess.security.filters.BasicTokenAuthenticationFilter;
import com.Atos.Webaccess.security.filters.TokenAuthenticationFilter;
import com.Atos.Webaccess.security.models.BasicAuthenticationEntryPoint;
import com.Atos.Webaccess.security.services.AuthTokenService;
import com.google.gson.Gson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final UserService userService;
    private final Gson gson;
    private final AuthTokenService authTokenService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ParametreService parametreService;
    private final HpasswordService hpasswordService;

    public SecurityConfiguration(
            UserService userService,
            Gson gson,
            AuthTokenService authTokenService,
            BCryptPasswordEncoder passwordEncoder,
            ParametreService parametreService,
            HpasswordService hpasswordService) {

        this.userService = userService;
        this.gson = gson;
        this.authTokenService = authTokenService;
        this.passwordEncoder = passwordEncoder;
        this.parametreService = parametreService;
        this.hpasswordService = hpasswordService;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationManager authenticationManager) throws Exception {

        http
            .cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable())
            .userDetailsService(userService)
            .httpBasic(basic -> basic
                .authenticationEntryPoint(
                    new BasicAuthenticationEntryPoint(gson)
                )
            );

        BasicTokenAuthenticationFilter basicTokenAuthenticationFilter =
                new BasicTokenAuthenticationFilter(
                    authenticationManager,
                    authTokenService,
                    userService,
                    passwordEncoder,
                    parametreService,
                    hpasswordService
                );

        TokenAuthenticationFilter tokenAuthenticationFilter =
                new TokenAuthenticationFilter(
                    authTokenService,
                    userService,
                    passwordEncoder
                );

        http.addFilter(basicTokenAuthenticationFilter);

        http.addFilterBefore(
            tokenAuthenticationFilter,
            BasicAuthenticationFilter.class
        );

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedMethods(
                        "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"
                    )
                    .allowCredentials(true)
                    .allowedHeaders("*");
            }
        };
    }
}
