package com.example.config;

import java.util.List;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.filter.JwtFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

	private final JwtFilter filter;
	
	public SecurityConfig(JwtFilter filter) {
		this.filter = filter;
	}
	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService servs) {

	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(servs);
	    provider.setPasswordEncoder(passwordEncoder());

	    return new ProviderManager(provider);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry){
				registry.addMapping("/**")
				.allowedHeaders("*")
				.allowedOrigins("http://localhost:5173")
				.allowedMethods("GET","POST","PUT","DELETE")
				.allowCredentials(true);
			}
		};
		}
 
	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

	
	@Bean
	public SecurityFilterChain securityfilter(HttpSecurity http) throws Exception{
		http
		.cors(cors -> {})  
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth
			    .requestMatchers("/auth/**").permitAll()
			    .requestMatchers("/images/**").permitAll()
			    .requestMatchers("/RoomOwner/**").hasRole("RoomOwner")
			    .requestMatchers("/user/**").hasRole("USER")
			    .requestMatchers("/rooms").permitAll()
			    .anyRequest().authenticated()
			)
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				);
		http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
}
