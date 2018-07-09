/**
 * 
 */
package org.dtodo1paco.samples.urlshortener.repository;

import org.dtodo1paco.samples.urlshortener.model.ServiceUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author pac
 *
 */
public interface ServiceUserRepository extends MongoRepository<ServiceUser, String> {

	/**
	 * Find by PK
	 * @param userName
	 * @return
	 */
	public ServiceUser findByUserName(String userName);

}
