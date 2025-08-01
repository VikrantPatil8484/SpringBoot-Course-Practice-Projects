package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails normal = User.withUsername("Vikrant")
//                .password(passwordEncoder().encode("Krant"))
//                .roles("NORMAL")
//                //now here we need to encode our password
//                .build();
//        
//        UserDetails admin = User.withUsername("vicky")
//        		.password(passwordEncoder().encode("patil"))
//                .roles("ADMIN")
//                
//                .build();
//
//        return new InMemoryUserDetailsManager(normal, admin);
//    }
//
//    // Optional: use NoOpPasswordEncoder for plain text passwords (not for production)
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//    	return new BCryptPasswordEncoder();
//    }

	// using spring secutiy fetching db users
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//form based authentication using security filter chain
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.authorizeRequests().anyRequest().authenticated().and().
//		formLogin().loginPage("login.html").loginProcessingUrl("/process-url").defaultSuccessUrl("/dashboard")
//				.failureUrl("error").and().logout().logoutUrl("/logout");
//		return http.build();
//	}

	//basuc authentication via api - this will send username and password inside the headers
	@Bean 
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf().disable().cors().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
		return http.build();
	}
}