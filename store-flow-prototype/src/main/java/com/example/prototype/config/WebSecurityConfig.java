package com.example.prototype.config;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.prototype.exceptions.CustomAccessDeniedHandler;
import com.example.prototype.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	private PasswordEncoder passwordEncoder; // ดึงมาจาก AppConfig ที่เราสร้างแยกไว้

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private UserDetailsServiceImp userDetailsServiceImp;

	private final JwtAuthFilter jwtAuthFilter;
	private final UserDetailsService userDetailsService;

	// Constructor injection for required dependencies
	public WebSecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsService userDetailsService) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailsService = userDetailsService;
	}

	/*
	 * Main security configuration Defines endpoint access rules and JWT filter
	 * setup
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("securityFilterChain working !!!!");
		logger.info("securityFilterChain working !!!!");
		http
				// Disable CSRF (not needed for stateless JWT)
				// 1. ต้องเปิดใช้ CORS ตรงนี้เพื่อให้ Filter ทำงาน
				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(List.of("http://localhost:4200"));
					config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
					config.setAllowedHeaders(List.of("*"));
					config.setExposedHeaders(List.of("Custom-Header")); // สำคัญ: เพื่อให้ Angular อ่าน Header ได้
					config.setAllowCredentials(true);
					return config;
				})).csrf(csrf -> csrf.disable())
				
				/* this point use custom Header.
				 .headers(headers -> headers
			            .addHeaderWriter(new StaticHeadersWriter("X-Custom-Header", "Hello-World"))
			            .addHeaderWriter(new StaticHeadersWriter("X-App-Version", "1.2.3")))
			    */
				
				//Config error exception custom.
				.exceptionHandling(exception -> exception.accessDeniedHandler(new CustomAccessDeniedHandler()))
				
				// Configure endpoint authorization
				.authorizeHttpRequests(auth -> auth
						// Public endpoints
						.requestMatchers("/actuator/**", "/auth/**", "/health",
								"/v3/api-docs/**",
				                "/swagger-ui/**",
				                "/swagger-ui.html").permitAll()

						// Role-based endpoints
						.requestMatchers("/user/**").hasAnyAuthority("USER","ADMINISTRATOR")
						.requestMatchers("/auth/admin/**").hasAnyAuthority("ADMINISTRATOR")

						// All other endpoints require authentication
						.anyRequest().authenticated())

				// Stateless session (required for JWT)
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Set custom authentication provider
				.authenticationProvider(authenticationProvider())

				// Add JWT filter before Spring Security's default filter
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	/*
	 * Authentication provider configuration Links UserDetailsService and
	 * PasswordEncoder
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		logger.info("authenticationProvider working !!!!");
		System.out.println("authenticationProvider working !!!!");
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsServiceImp);
		System.out.println("provider success !!!");
		provider.setPasswordEncoder(appConfig.passwordEncoder());
		return provider;
	}

	/*
	 * Authentication manager bean Required for programmatic authentication (e.g.,
	 * in /generateToken)
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
