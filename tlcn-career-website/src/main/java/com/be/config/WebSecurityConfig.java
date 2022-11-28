package com.be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Bean
	protected BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	protected AuthTokenFilter authTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http, CustomAccessDeniedHandler customAccessDeniedHandler,
			CustomAuthenticationEntryPoint authenticationExceptionHandling) throws Exception {
		http.cors().and().csrf().disable().httpBasic().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.authenticationEntryPoint(authenticationExceptionHandling).and().exceptionHandling()
				.accessDeniedHandler(customAccessDeniedHandler);

		http.authorizeRequests()
				.antMatchers("/api/admin/login", "/api/admin/signup", "/api/employer/login", "/api/employer/signup",
						"/api/user/login", "/api/user/signup", "/api/employer/sendpasswordresetcode",
						"/api/employer/resetpassword", "/api/user/sendpasswordresetcode", "/api/user/resetpassword",
						"/api/employer/infomation/**","/api/employer/reset-password","/api/user/reset-password",
						"/api/employer/confirm-email","/api/user/confirm-email")
				.permitAll()
				/*
				 * .and() .authorizeRequests() .antMatchers("/api/momo/notify")
				 * .access("hasIpAddress('127.0.0.1')")
				 */
				.and().authorizeRequests().antMatchers("/api/admin/**").hasRole("ADMIN").and().authorizeRequests()
				.antMatchers("/api/user/**").hasRole("USER").and().authorizeRequests().antMatchers("/api/employer/**")
				.hasRole("EMPLOYER").and().authorizeRequests().antMatchers("/**").permitAll();

		http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	protected CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
