/**
 * 
 */
package org.dtodo1paco.samples.urlshortener.repository;

import java.util.List;

import org.dtodo1paco.samples.urlshortener.model.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author pac
 *
 */
public interface ResourceRepository extends MongoRepository<Resource, String> {

	/**
	 * Finder for users of mapped link
	 * @param shortened
	 * @return
	 */
	public Resource findByShortened(String shortened);
	public List<Resource> findByOwner(String owner);

}
