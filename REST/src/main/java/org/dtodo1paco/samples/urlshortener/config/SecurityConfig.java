package org.dtodo1paco.samples.urlshortener.config;

import org.dtodo1paco.samples.auth.jwt.JWTAuthenticationFilter;
import org.dtodo1paco.samples.auth.jwt.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_USER = "USER";

	@Autowired
	private MyAppUserDetailsService myAppUserDetailsService;

	@Autowired
	private MyAppAuthenticationEntryPoint myAppAuthenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().authenticationEntryPoint(
				new Http403ForbiddenEntryPoint());

		// TODO:
		// enable
		// filters
		http
			.csrf()
				.disable()
			.cors()
				.disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS)
					.permitAll()
				.antMatchers(HttpMethod.GET, "/auth/")
					.permitAll()
				.antMatchers(HttpMethod.POST, "/url/*")
					.permitAll()
				.antMatchers(HttpMethod.GET, "/auth/test")
					.authenticated()
				.antMatchers("/user/**")
					.authenticated()
				// .antMatchers("/user/**").hasAnyRole(_ROLES) // TODO: set
				// roles for user when JWTAuth. Roles must be in the token and
				// when auth, passed to User object
			.and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager()))
			.httpBasic().realmName(MyAppAuthenticationEntryPoint.REALM)
				.authenticationEntryPoint(myAppAuthenticationEntryPoint);
		;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(myAppUserDetailsService).passwordEncoder(
				passwordEncoder);
	}

	private static final String[] _ROLES = { ROLE_ADMIN, ROLE_USER };

}