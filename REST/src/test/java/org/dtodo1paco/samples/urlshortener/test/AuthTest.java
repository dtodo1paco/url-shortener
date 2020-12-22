/**
 * 
 */
package org.dtodo1paco.samples.urlshortener.test;

import static org.dtodo1paco.samples.urlshortener.model.UserConstants.ROLE_ADMIN;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.dtodo1paco.samples.urlshortener.MyApplication;
import org.dtodo1paco.samples.urlshortener.model.Resource;
import org.dtodo1paco.samples.urlshortener.model.ServiceUser;
import org.dtodo1paco.samples.urlshortener.repository.ServiceUserRepository;
import org.junit.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	public static final String USERNAME = "dtodo1paco";
	public static final String PASSWORD = "mi.123456";

	@LocalServerPort
	private int port;

	@Autowired
	private ServiceUserRepository serviceUserRepository;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	private static String token = null;

	private static boolean initialized = false;

	public static ServiceUser getDefaultUser () {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		ServiceUser defaultUser = new ServiceUser();
		defaultUser.setEnabled(true);
		defaultUser.setFullName("Admin test user");
		defaultUser.setPassword(passwordEncoder.encode(PASSWORD));
		defaultUser.setUsername(USERNAME);
		defaultUser.setRole(ROLE_ADMIN);
		return defaultUser;
	}

	@Before
	public void initDb() {
		if (initialized) return;
		serviceUserRepository.save(AuthTest.getDefaultUser());
		AuthTest.initialized = true;
	}

	@Before
	public void checkDb() {
		long usersCount = serviceUserRepository.count();
		assertEquals(1, usersCount);
	}

	@Test
	public void t01_auth_get_token() {
		HttpEntity<Resource> entity = new HttpEntity<Resource>(
				TestUtils.createAuthHeaders(AuthTest.USERNAME,
						AuthTest.PASSWORD));
		ResponseEntity<HashMap> response = restTemplate.exchange(
				TestUtils.createURLWithPort("/auth/", port), HttpMethod.GET,
				entity, HashMap.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		AuthTest.token = String.valueOf(response.getBody().get("Authorization")).substring("Bearer ".length());
	}

	@Test
	public void t02_auth_test() {
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
