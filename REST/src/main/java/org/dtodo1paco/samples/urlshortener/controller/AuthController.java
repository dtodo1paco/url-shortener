package org.dtodo1paco.samples.urlshortener.controller;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dtodo1paco.auth.AuthUtils;
import org.dtodo1paco.samples.auth.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private HttpServletRequest context;

	// GET
	@GetMapping("test")
	public ResponseEntity<String> test() {
		return new ResponseEntity<String>(AuthUtils.getUserName(context),
				HttpStatus.OK);
	}

	// GET
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> doAuth(Principal principal,
			Authentication authentication) {
		if (authentication == null) {
			HttpHeaders headers = new HttpHeaders();

			headers.setLocation(URI.create("/#/login"));
			return new ResponseEntity<Map<String, Object>>(headers,
					HttpStatus.MOVED_PERMANENTLY);
		}
		Map<String, Object> response = new HashMap<String, Object>();
		String token = JwtUtil.buildToken(authentication);

		response.put(JwtUtil.HEADER_AUTH, JwtUtil.HEADER_BEARER + token);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}