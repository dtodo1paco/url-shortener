package org.dtodo1paco.samples.auth.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		String header = req.getHeader(JwtUtil.HEADER_AUTH);
		if (header == null || !header.startsWith(JwtUtil.HEADER_BEARER)) {
			//System.out.println("+++ falling back to basic: " + req.getMethod());
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = JwtUtil
				.getAuthentication(req);
		//JwtUtil.addAuthentication(res, authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

}