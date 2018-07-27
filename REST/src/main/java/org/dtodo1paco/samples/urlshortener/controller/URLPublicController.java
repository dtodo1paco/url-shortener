package org.dtodo1paco.samples.urlshortener.controller;

import java.net.URI;
import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.dtodo1paco.auth.AuthUtils;
import org.dtodo1paco.samples.urlshortener.URLShortenerUtil;
import org.dtodo1paco.samples.urlshortener.model.Resource;
import org.dtodo1paco.samples.urlshortener.model.ResourceVisit;
import org.dtodo1paco.samples.urlshortener.repository.ResourceRepository;
import org.dtodo1paco.samples.urlshortener.repository.ResourceVisitRepository;
import org.dtodo1paco.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

//TODO: remove Cors
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class URLPublicController {

	@Autowired
	private HttpServletRequest context;

	@Autowired
	private ResourceRepository resourceRepo;

	@Autowired
	private ResourceVisitRepository visitRepo;

	// SAVE
	@PostMapping("url")
	public ResponseEntity<Resource> add(@RequestBody Resource item,
			UriComponentsBuilder builder) {
		Resource result = null;
		String url = item.getSource();
		String code = URLShortenerUtil.doShort(url);
		Resource found = resourceRepo.findByShortened(code);
		if (found != null) {
			result = found;
			// TODO: update statistics
		} else {
			// TODO: update statistics
			result = new Resource();
			result.setShortened(code);
			result.setSource(url);
			result.setOwner(AuthUtils.getUserName(context));
			result.setCreated(new Date());
			result = resourceRepo.save(result);
		}
		if (result == null) {
			return new ResponseEntity<Resource>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/url/{id}")
				.buildAndExpand(result.getShortened()).toUri());
		return new ResponseEntity<Resource>(result, headers, HttpStatus.CREATED);
	}

	// TRANSLATE AND FOLLOW
	@GetMapping("/{code}")
	public ResponseEntity<Void> follow(@PathVariable("code") String code) {
		Resource found = null;
		if (code.length() == 8) {
			found = resourceRepo.findByShortened(code);
		}
		if (found != null) {
			saveVisit(found, code);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(found.getSource()));
			// TODO: update statistics
			return new ResponseEntity<Void>(headers,
					HttpStatus.MOVED_PERMANENTLY);
		} else {
			// TODO: update statistics
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}

	// GET
	@GetMapping("url/{code}")
	public ResponseEntity<Resource> get(@PathVariable("code") String code) {
		String key = code;
		Resource item = resourceRepo.findByShortened(key);
		return new ResponseEntity<Resource>(item, HttpStatus.OK);
	}
	/**
	 * Gets remote address of request by headers if present
	 * @return
	 */
	private String getRemoteAddr() {
		String ipFromHeader = context
				.getHeader(URLShortenerUtil.HEADER_FORWARDED_FOR);
		if (ipFromHeader != null && ipFromHeader.length() > 0) {
			return ipFromHeader;
		}
		return context.getRemoteAddr();
	}
	
	/**
	 * Saves visit of resource
	 * 
	 * @param resource
	 * @param code
	 */
	private void saveVisit(Resource resource, String code) {
		Date date = new Date();
		ResourceVisit visit = new ResourceVisit();
		visit.setDate(DateUtil.atStartOfDay(date));
		visit.setMethod(context.getMethod());
		visit.setReferer(context.getHeader(URLShortenerUtil.HEADER_REFERER));
		visit.setRemoteAddr(getRemoteAddr());
		visit.setTime(DateUtil.getTimeAsString(date));
		if (!StringUtils.isEmpty(code)) {
			if (!code.equals("error")) {
				if (resource != null) {
					visit.setResourceId(resource.getShortened());
				} else {
					visit.setResourceId(code);
				}
			}
		}
		Principal user = context.getUserPrincipal();
		if (user != null) {
			visit.setUserName(user.getName());
		}
		visitRepo.save(visit);
	}

}