/**
 * 
 */
package org.dtodo1paco.samples.urlshortener.repository;

import java.time.Instant;
import java.util.List;

import org.dtodo1paco.samples.urlshortener.model.Resource;
import org.dtodo1paco.samples.urlshortener.model.ResourceVisit;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author pac
 *
 */
public interface ResourceVisitRepository extends MongoRepository<ResourceVisit, String> {

	/**
	 * Finder to filter by resource accesed
	 * @param resourceId
	 * @return
	 */
	public List<ResourceVisit> findByResourceId(String resourceId);
	/**
	 * Finder to filter by date
	 * @param id
	 * @return
	 */
	public List<ResourceVisit> findByDateBetween(Instant from, Instant to);


}
