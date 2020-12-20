/**
 * 
 */
package org.dtodo1paco.samples.urlshortener.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.dtodo1paco.samples.urlshortener.MyApplication;
import org.dtodo1paco.samples.urlshortener.model.Resource;
import org.dtodo1paco.samples.urlshortener.repository.ResourceRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pac
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class URLShortenerTest {

	private static final String URL_TO_MAP = "https://www.google.es/search?q=sample+urls&oq=sample+urls&aqs=chrome..69i57j0l5.1185j0j9&sourceid=chrome&ie=UTF-8";
	private static final String URL_TO_MAP_SHORTENED = "f1f74d3e";
	private static final String USERNAME = "dtodo1paco";
	private static final String PASSWORD = "mi.123456";
	private static final HttpHeaders DEFAULT_HEADERS = new HttpHeaders();

	@Autowired
	private ResourceRepository resourceRepo;

	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	private static long initialResources;
	private static long currentResources;

	private static StringBuilder output = new StringBuilder("");

	private static boolean initialized = false;

	public static void initClass() {
	}

	@Before
	public void init() {
		long total = resourceRepo.count();
		if (!initialized) {
			initialResources = total;
			initialized = true;
		}
		debug("current resources: " + total);
		output.append("a");
	}

	@Test
	public void t01_addResource() {
		Resource res = getSampleResource(URLShortenerTest.URL_TO_MAP);
		HttpEntity<Resource> entity = new HttpEntity<Resource>(res,
				DEFAULT_HEADERS);

		ResponseEntity<Resource> response = restTemplate.exchange(
				TestUtils.createURLWithPort("/url", port), HttpMethod.POST,
				entity, Resource.class);
		Resource resp = response.getBody();
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(res.getSource(), resp.getSource());
		debug("added resource: " + resp);
	}

	@Test
	public void t10_searchResource() {
		String code = URLShortenerTest.URL_TO_MAP_SHORTENED;
		Resource res = new Resource();
		res.setShortened(code);
		HttpEntity<Resource> entity = new HttpEntity<Resource>(res,
				DEFAULT_HEADERS);
		ResponseEntity<Resource> response = restTemplate.exchange(
				TestUtils.createURLWithPort("/url/" + code, port),
				HttpMethod.GET, entity, Resource.class);
		debug("resource found [" + code + "] was [" + response + "]");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(URLShortenerTest.URL_TO_MAP, response.getBody()
				.getSource());
	}

	@Test
	public void t11_searchResources() {
		HttpEntity<Resource> entity = new HttpEntity<Resource>(
				TestUtils.createAuthHeaders(URLShortenerTest.USERNAME,
						URLShortenerTest.PASSWORD));
		ResponseEntity<List> response = restTemplate.exchange(
				TestUtils.createURLWithPort("/user/urls", port),
				HttpMethod.GET, entity, List.class);
		debug("allResources count: " + response.getBody().size());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(initialResources + 1, response.getBody().size());
	}

	@Test
	public void t20_followResource() {
		String code = URLShortenerTest.URL_TO_MAP_SHORTENED;
		ResponseEntity<Void> response = restTemplate.exchange(
				TestUtils.createURLWithPort("/" + code, port), HttpMethod.GET,
				null, Void.class);
		debug("follow [" + code + "] was [" + response + "]");

		assertEquals(HttpStatus.MOVED_PERMANENTLY, response.getStatusCode());
		assertEquals(URLShortenerTest.URL_TO_MAP, response.getHeaders()
				.getLocation().toString());
	}

	@Test
	public void t_30_updResource() {
		HttpHeaders headers = TestUtils.createAuthHeaders(
				URLShortenerTest.USERNAME, URLShortenerTest.PASSWORD);
		String code = URLShortenerTest.URL_TO_MAP_SHORTENED;
		Resource res = new Resource();
		res.setShortened(code);
		res.setSource(URLShortenerTest.URL_TO_MAP);
		res.setOwner(URLShortenerTest.USERNAME);
		res.setCreated(new Date());
		HttpEntity<Resource> entity = new HttpEntity<Resource>(res, headers);
		ResponseEntity<Resource> response = restTemplate.exchange(
				TestUtils.createURLWithPort("/user/url", port), HttpMethod.PUT,
				entity, Resource.class);
		debug("updated [" + code + "] was [" + response + "]");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(URLShortenerTest.USERNAME, response.getBody().getOwner());
	}

	@Test
	public void t_40_delResource() {
		HttpHeaders headers = TestUtils.createAuthHeaders(
				URLShortenerTest.USERNAME, URLShortenerTest.PASSWORD);
		String code = URLShortenerTest.URL_TO_MAP_SHORTENED;
		Resource res = new Resource();
		res.setShortened(code);
		HttpEntity<Resource> entity = new HttpEntity<Resource>(res, headers);
		ResponseEntity<Resource> response = restTemplate.exchange(
				TestUtils.createURLWithPort("/user/url/" + code, port),
				HttpMethod.DELETE, entity, Resource.class);
		debug("deleted [" + code + "] was [" + response + "]");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(URLShortenerTest.URL_TO_MAP, response.getBody()
				.getSource());
	}

	@After
	public void afterTest() {
		currentResources = resourceRepo.count();
	}

	@AfterClass
	public static void assertOutput() {
		assertEquals(initialResources, currentResources);
	}

	private Resource getSampleResource() {
		int random = Double.valueOf(Math.floor(Math.random() * 20)).intValue();
		Resource res = new Resource();
		res.setCreated(new Date());
		res.setOwner("dtodo1paco");
		res.setShortened("https://goo.gl/" + random);
		res.setSource("https://google.com/" + random);
		return res;
	}

	private Resource getSampleResource(String src) {
		Resource res = new Resource();
		res.setSource(src);
		return res;
	}

	private void debug(String msg) {
		System.out.println("\n");
		System.out.println(msg);
		System.out.println("\n");
	}
}
