/**
 * 
 */
package org.dtodo1paco.samples.urlshortener;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.dtodo1paco.samples.urlshortener.model.Resource;

import com.google.common.hash.Hashing;

/**
 * @author pac
 *
 */
public class URLShortenerUtil {

	public static final String DOMAIN = "http://localhost:8080/";
	
	public static final String ATTRIBUTE_NAME_CODE = "org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping";;
	public static final String HEADER_USER_AGENT = "user-agent";
	public static final String HEADER_REFERER = "referer";
	public static final String HEADER_FORWARDED_FOR = "X-FORWARDED-FOR";
	
	public static String doShort(String url) {
		final String key = Hashing.murmur3_32()
				.hashString(url, StandardCharsets.UTF_8).toString();
		return key;
	}
	
	public static String buildURL(String key) {
		StringBuilder sb = new StringBuilder(URLShortenerUtil.DOMAIN);
		sb.append(key);
		return sb.toString();
	}

	public static String getKeyfromURL(String url) throws UnsupportedEncodingException {
		// TODO: validate URL
		return UUID.nameUUIDFromBytes(url.getBytes()).toString();
	}
	
	public boolean validateURL (String url) throws URISyntaxException {
		URI uri = new URI(url);
		if (
				uri.getScheme().startsWith("http")
			) {
			return true;
		}
		return false;
	}
}
