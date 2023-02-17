package it.epicode.be.devicedistribution.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.epicode.be.devicedistribution.auth.AuthEntryPointJwt;
import it.epicode.be.devicedistribution.auth.AuthTokenFilter;
import it.epicode.be.devicedistribution.services.AccessDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Autowired
	AccessDetailsService adS;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(adS);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//Auth Calls always permitted
	//Get Calls permitted with Standard Token
	//Post, Put, and Delete Calls permitted only with Admin Token
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/api/auth/**").permitAll() 
				.antMatchers(HttpMethod.POST, "/api/users").hasAuthority("Administrator")				
				.antMatchers(HttpMethod.POST, "/api/tablets").hasAuthority("Administrator")
				.antMatchers(HttpMethod.POST, "/api/notebooks").hasAuthority("Administrator")
				.antMatchers(HttpMethod.POST, "/api/smartphones").hasAuthority("Administrator")
				.antMatchers(HttpMethod.PUT, "/api/users/{id}").hasAuthority("Administrator")
				.antMatchers(HttpMethod.PUT, "/api/devices/{id}").hasAuthority("Administrator")
				.antMatchers(HttpMethod.PUT, "/api/tablets/{id}").hasAuthority("Administrator")
				.antMatchers(HttpMethod.PUT, "/api/notebooks/{id}").hasAuthority("Administrator")
				.antMatchers(HttpMethod.PUT, "/api/smartphones/{id}").hasAuthority("Administrator")			
				.antMatchers(HttpMethod.DELETE, "/api/users/{id}").hasAuthority("Administrator")				
				.antMatchers(HttpMethod.DELETE, "/api/devices/{id}").hasAuthority("Administrator")				
				.antMatchers(HttpMethod.DELETE, "/api/tablets/{id}").hasAuthority("Administrator")				
				.antMatchers(HttpMethod.DELETE, "/api/notebooks/{id}").hasAuthority("Administrator")				
				.antMatchers(HttpMethod.DELETE, "/api/smartphones/{id}").hasAuthority("Administrator")
				.anyRequest().authenticated();

		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
