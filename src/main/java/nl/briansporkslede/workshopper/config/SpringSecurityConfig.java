package nl.briansporkslede.workshopper.config;

import nl.briansporkslede.workshopper.filter.JwtRequestFilter;
import nl.briansporkslede.workshopper.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity
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



    // Authorizatie met jwt
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeRequests()
/*
                .antMatchers(HttpMethod.GET,"/users").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.GET, "/users/**").authenticated()
                .antMatchers(HttpMethod.POST, "/users").permitAll()     // for testing purposes
                .antMatchers(HttpMethod.PUT,"/users/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "${apiPrefix}/workshops").hasAnyAuthority("PLANNER", "STUDENT", "MENTOR", "TEACHER")
                .antMatchers(HttpMethod.POST, "${apiPrefix}/workshops").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.POST, "${apiPrefix}/workshops").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.PUT, "${apiPrefix}/workshops/**").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.DELETE, "${apiPrefix}/workshops/**").hasAuthority("PLANNER")

                .antMatchers(HttpMethod.GET, "${apiPrefix}/teachers").hasAnyAuthority("ADMIN", "PLANNER", "MENTOR", "TEACHER")
                .antMatchers(HttpMethod.POST, "${apiPrefix}/teachers").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "${apiPrefix}/teachers/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "${apiPrefix}/teachers/**").hasAnyAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "${apiPrefix}/reservations").hasAnyAuthority("PLANNER", "STUDENT", "MENTOR")
                .antMatchers(HttpMethod.POST, "${apiPrefix}/reservations").hasAuthority("STUDENT")
                .antMatchers(HttpMethod.PUT, "${apiPrefix}/reservations/**").hasAnyAuthority("PLANNER", "STUDENT")
                .antMatchers(HttpMethod.DELETE, "${apiPrefix}/reservations/**").hasAnyAuthority("STUDENT","MENTOR","PLANNER")

                .antMatchers(HttpMethod.GET, "${apiPrefix}/bookings").hasAnyAuthority("PLANNER", "STUDENT", "MENTOR")
                .antMatchers(HttpMethod.POST, "${apiPrefix}/bookings").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.PUT, "${apiPrefix}/bookings/**").hasAuthority("PLANNER")
                .antMatchers(HttpMethod.DELETE, "${apiPrefix}/bookings/**").hasAnyAuthority("PLANNER")

                .antMatchers(HttpMethod.GET, "${apiPrefix}/students").hasAnyAuthority("PLANNER", "MENTOR", "TEACHER")
                .antMatchers(HttpMethod.POST, "${apiPrefix}/students").hasAuthority("MENTOR")
                .antMatchers(HttpMethod.PUT, "${apiPrefix}/students").hasAuthority("MENTOR")
                .antMatchers(HttpMethod.DELETE, "${apiPrefix}/students/**").hasAuthority("MENTOR")

                .antMatchers(HttpMethod.GET, "/pairs").hasAnyAuthority( "MENTOR")
                .antMatchers(HttpMethod.POST, "/pairs").hasAuthority("MENTOR")
                .antMatchers(HttpMethod.PUT, "/pairs/**").hasAuthority("MENTOR")
                .antMatchers(HttpMethod.DELETE, "/pairs/**").hasAnyAuthority("MENTOR")
*/
                .antMatchers(HttpMethod.POST, "/auth").permitAll()    // everyone is allowed to authenticate
                .antMatchers("/authenticated").authenticated()
                .antMatchers("/authenticate").permitAll()

                .antMatchers("/**").permitAll()         // allow everything from everyone for testing purposes

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
