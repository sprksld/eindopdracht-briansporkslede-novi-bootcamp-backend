package nl.briansporkslede.workshopper.config;

import nl.briansporkslede.workshopper.service.CustomUserDetailsService;
import nl.briansporkslede.workshopper.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // PasswordEncoderBean. Deze kun je overal in je applicatie injecteren waar nodig.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }


    // Autorisatie met jwt
    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeRequests()

//                .antMatchers("/**").permitAll()         // allow everything from everyone for testing purposes

                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()  // everyone is allowed to authenticate
                .antMatchers(HttpMethod.GET, "/authenticated").authenticated()
                .antMatchers(HttpMethod.GET, "/users/**").authenticated()

                .antMatchers(HttpMethod.GET, "/download/default.jpg").permitAll()
                .antMatchers(HttpMethod.POST, "/upload-default").hasAuthority("PLANNER")

                .antMatchers(HttpMethod.GET, "/api/v1/workshops").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/workshops/**").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.POST, "/api/v1/workshops/**").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.POST, "/api/v1/bookings/batch").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.GET, "/api/v1/workshops/rooms").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.GET, "/api/v1/workshops/categories").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.GET, "/api/v1/teachers/list").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.GET, "/api/v1/students").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.GET, "/api/v1/reservations").hasAnyAuthority("PLANNER", "MENTOR")

                .antMatchers(HttpMethod.GET, "/api/v1/bookings/forme").hasAuthority("STUDENT")
                .antMatchers(HttpMethod.GET, "/api/v1/workshops/upcoming").hasAuthority("STUDENT")
                .antMatchers(HttpMethod.GET, "/api/v1/reservations/upcoming").hasAuthority("STUDENT")
                .antMatchers(HttpMethod.PATCH, "/api/v1/workshops/like/**").hasAuthority("STUDENT")

                .antMatchers(HttpMethod.GET, "/api/v1/workshops/ihavetoteach").hasAuthority("TEACHER")

                .antMatchers(HttpMethod.GET, "/api/v1/students/onlymine").hasAuthority("MENTOR")
                .antMatchers(HttpMethod.GET, "/api/v1/workshops/mystudentscanchoosefrom").hasAuthority("MENTOR")
                .antMatchers(HttpMethod.GET, "/api/v1/reservations/bymystudents").hasAuthority("MENTOR")
                .antMatchers(HttpMethod.GET, "/api/v1/bookings/formystudents").hasAuthority("MENTOR")
                .antMatchers(HttpMethod.GET, "/api/v1/bookings/attended-and-feedback").hasAuthority("MENTOR")
                .antMatchers(HttpMethod.POST, "/api/v1/students").hasAuthority("MENTOR")
                .antMatchers(HttpMethod.DELETE, "/api/v1/students/**").hasAuthority("MENTOR")

                .antMatchers(HttpMethod.GET, "/**").hasAuthority("ADMIN")
                .antMatchers("/**").denyAll()

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
