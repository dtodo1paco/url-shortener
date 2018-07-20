/**
 * 
 */
package org.dtodo1paco.samples.urlshortener.test;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.codec.Base64;

/**
 * @author pac
 *
 */
public class TestUtils {


	public static final String createURLWithPort(String uri, int port) {
		return "http://localhost:" + port + uri;
	}

	public static final HttpHeaders createAuthHeaders(String username, String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encode(auth.getBytes(Charset
					.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}
	public static final HttpHeaders createAuthHeaders(String token) {
		return new HttpHeaders() {
			{
				String authHeader = "Bearer " + token;
				set("Authorization", authHeader);
			}
		};
	}

}
