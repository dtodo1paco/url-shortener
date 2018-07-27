/**
 * 
 */
package org.dtodo1paco.samples.auth.jwt;

/**
 * @author pac
 *
 */

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dtodo1paco.samples.urlshortener.model.ServiceUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse res) throws AuthenticationException {
		try {
			ServiceUser creds = new ObjectMapper().readValue(
					req.getInputStream(), ServiceUser.class); 
			
			// TODO: set authorities for user to manage Auth in SecurityConfig
			return authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(creds
							.getUserName(), creds.getPassword(), Collections
							.emptyList()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain, Authentication auth)
			throws IOException, ServletException {
		JwtUtil.addAuthentication(res,auth);

	}
}