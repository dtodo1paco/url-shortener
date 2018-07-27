/**
 * 
 */
package org.dtodo1paco.samples.auth.jwt;

/**
 * @author pac
 *
 */

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.dtodo1paco.samples.urlshortener.model.ServiceUser;
import org.dtodo1paco.util.StringUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static java.util.Collections.emptyList;

public class JwtUtil {

	/**
	 * 10 minutes of expiration time
	 */
	public static final long EXPIRATION_MILLIS = 600000;

	public static final String HASH = "n0h@yni3v33nstm@urice";
	public static final String HEADER_AUTH = "Authorization";
	public static final String HEADER_BEARER = "Bearer ";
	public static final String BEARER_TOKEN = "Bearer";

	/**
	 * Adds JWT token to response
	 * 
	 * @param res
	 * @param username
	 */
	public static void addAuthentication(HttpServletResponse res,
			Authentication auth) {
		String token = JwtUtil.buildToken(auth);
		res.addHeader(JwtUtil.HEADER_AUTH, JwtUtil.HEADER_BEARER + token);
	}

	/**
	 * Builds a token with user info to be send to the client
	 * 
	 * @param auth
	 * @return
	 */
	public static String buildToken(Authentication auth) {
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		
/*
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for (GrantedAuthority role : roles) {
			sb.append(role.getAuthority());
			n = n + 1;
			if (n < roles.size()) {
				sb.append(StringUtil.PIPE);
			}
		}
		*/

		// TODO: send Roles in token to let the user decide the Role to play
		String token = Jwts
				.builder()
				.setSubject(setSubject(auth.getPrincipal()))
				.setIssuedAt(new Date())
				.setExpiration(
						new Date(System.currentTimeMillis()
								+ JwtUtil.EXPIRATION_MILLIS))
				.signWith(SignatureAlgorithm.HS512, JwtUtil.HASH).compact();
		return token;
	}

	private static String setSubject(Object principal) {
		if (principal instanceof User) {
			return ((User)principal).getUsername();
		}
		return null;
	}

	/**
	 * Token validation
	 * 
	 * @param request
	 * @return
	 */
	public static UsernamePasswordAuthenticationToken getAuthentication(
			HttpServletRequest request) {
		String token = request.getHeader(JwtUtil.HEADER_AUTH);
		if (token != null && token.startsWith(JwtUtil.HEADER_BEARER)) {
			try {
				String user = Jwts
						.parser()
						.setSigningKey(JwtUtil.HASH)
						.parseClaimsJws(
								token.replace(JwtUtil.BEARER_TOKEN, ""))
						.getBody().getSubject();
				return user != null ? new UsernamePasswordAuthenticationToken(
						user, null, Collections.emptyList()) : null;
			} catch (ExpiredJwtException e) {
				request.setAttribute("error", e.getMessage());
				return null;
			}
		}
		return null;
	}
}