package org.dtodo1paco.samples.urlshortener.util;

import java.net.URI;

import org.apache.tomcat.util.codec.binary.Base64;
import org.dtodo1paco.samples.urlshortener.model.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class RestClientUtil {
	
	public static final String BASE_SERVER = "http://localhost:8080";
	public static final String BASE_SERVICE_URL = RestClientUtil.BASE_SERVER + "/url";
	
    private static HttpHeaders getHeaders() {
    	String credential="dtodo1paco:mi.123456";
    	//String credential="tarun:t123";
    	String encodedCredential = new String(Base64.encodeBase64(credential.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
     	headers.add("Authorization", "Basic " + encodedCredential);
    	return headers;
    }

    /**
     * 
     * @param url (i.e. "https://www.google.es/search?q=sample+urls&oq=sample+urls&aqs=chrome..69i57j0l5.1185j0j9&sourceid=chrome&ie=UTF-8")
     */
    public static void addMapping(String url) {
    	HttpHeaders headers = getHeaders();  
        RestTemplate restTemplate = new RestTemplate();
	    Resource res = new Resource();
	    res.setSource(url);
        HttpEntity<Resource> requestEntity = new HttpEntity<Resource>(res, headers);
        URI uri = restTemplate.postForLocation(RestClientUtil.BASE_SERVICE_URL, requestEntity);
        System.out.println(uri.getPath());
    }
    

//    public void updateArticleDemo() {
//    	HttpHeaders headers = getHeaders();  
//        RestTemplate restTemplate = new RestTemplate();
//	    String url = "http://localhost:8080/user/article";
//	    Article objArticle = new Article();
//	    objArticle.setArticleId(1);
//	    objArticle.setTitle("Update:Java Concurrency");
//	    objArticle.setCategory("Java");
//        HttpEntity<Article> requestEntity = new HttpEntity<Article>(objArticle, headers);
//        restTemplate.put(url, requestEntity);
//    }
//    public void deleteArticleDemo() {
//    	HttpHeaders headers = getHeaders();  
//        RestTemplate restTemplate = new RestTemplate();
//	    String url = "http://localhost:8080/user/article/{id}";
//        HttpEntity<Article> requestEntity = new HttpEntity<Article>(headers);
//        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, 4);        
//    }
    public static void main(String args[]) {
    	RestClientUtil util = new RestClientUtil();
        util.addMapping("https://www.google.es/search?q=sample+urls&oq=sample+urls&aqs=chrome..69i57j0l5.1185j0j9&sourceid=chrome&ie=UTF-8");
    }    
}
