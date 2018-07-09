package org.dtodo1paco.samples.urlshortener.controller;
import java.util.List;

import org.dtodo1paco.samples.urlshortener.model.Resource;
import org.dtodo1paco.samples.urlshortener.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class URLPrivateController {
	
	@Autowired
	private ResourceRepository repository;
	
	// GET
	@GetMapping("urls")
	public ResponseEntity<List<Resource>> getAll() {
		List<Resource> list = repository.findAll();
		return new ResponseEntity<List<Resource>>(list, HttpStatus.OK);
	}

	// UPDATE
	@PutMapping("url")
	public ResponseEntity<Resource> update(@RequestBody Resource item) {
		Resource result = repository.save(item);
		return new ResponseEntity<Resource>(result, HttpStatus.OK);
	}
	
	// REMOVE
	@DeleteMapping("url/{id}")
	public ResponseEntity<Resource> delete(@PathVariable("id") String id) {
		Resource item = repository.findByShortened(id);
		repository.delete(item);
		return new ResponseEntity<Resource>(item, HttpStatus.OK);
	}
}