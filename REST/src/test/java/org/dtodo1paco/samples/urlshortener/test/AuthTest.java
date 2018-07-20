/**
 * 
 */
package org.dtodo1paco.samples.urlshortener.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dtodo1paco.samples.urlshortener.MyApplication;
import org.dtodo1paco.samples.urlshortener.model.Resource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author pac
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthTest {

	private static final String USERNAME = "dtodo1paco";
	private static final String PASSWORD = "mi.123456";
	private static int TOKEN_LENGTH = 187;
	private static String HEADER_AUTH = "Authorization";

	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	public static void initClass() {
	}

	private static String token = null;

	@Before
	public void init() {
	}

	@Test
	public void t01_auth_get_token() throws JsonProcessingException,
			IOException {
		HttpEntity<Resource> entity = new HttpEntity<Resource>(
				TestUtils.createAuthHeaders(AuthTest.USERNAME,
						AuthTest.PASSWORD));
		ResponseEntity<HashMap> response = restTemplate.exchange(
				TestUtils.createURLWithPort("/auth/", port), HttpMethod.GET,
				entity, HashMap.class);
		AuthTest.token = String.valueOf(response.getBody().get(HEADER_AUTH));
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(AuthTest.TOKEN_LENGTH, token.length());
	}

	@Test
	public void t02_auth_test() throws JsonProcessingException, IOException {
		HttpEntity<Resource> entity = new HttpEntity<Resource>(
				TestUtils.createAuthHeaders(AuthTest.token));

		ResponseEntity<String> response = restTemplate.exchange(
				TestUtils.createURLWithPort("/auth/test", port),
				HttpMethod.GET, entity, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(AuthTest.USERNAME, response.getBody());
	}

	@After
	public void afterTest() {

	}

	@AfterClass
	public static void assertOutput() {
	}

	private void debug(String msg) {
		System.out.println("\n");
		System.out.println(msg);
		System.out.println("\n");
	}
}
