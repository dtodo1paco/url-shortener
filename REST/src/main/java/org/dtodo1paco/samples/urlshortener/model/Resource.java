/**
 * 
 */
package org.dtodo1paco.samples.urlshortener.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author pac
 *
 */
public class Resource {

	@Id
	private String shortened;
	private String source;
	@Indexed
	private String owner;
	private Date created;

	public Resource() {
	}

	public Resource(String source) {
		this.setSource(source);
	}

	@Override
	public String toString() {
		return String.format("{%s[source='%s', shortened='%s', owner='%', created='%s']}",
				getClass(), getSource(), getShortened(), getCreated());
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getShortened() {
		return shortened;
	}

	public void setShortened(String shortened) {
		this.shortened = shortened;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
