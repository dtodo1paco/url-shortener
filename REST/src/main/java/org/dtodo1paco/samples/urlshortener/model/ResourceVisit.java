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
public class ResourceVisit {

	/**
	 * A shorter and unique representation of source Resource to speed up queries
	 */
	@Id
	private String id;

	@Indexed
	private String resourceId;
	@Indexed
	private Date date;
	private String time;
	private String referer;
	private String remoteAddr;
	private String userAgent;
	private String method;
	private String userName;

	
	//private String location; //https://dev.maxmind.com/geoip/geoip2/web-services/
	

	public ResourceVisit() {
	}



	@Override
	public String toString() {
		return String.format("{%s[id=%s, date='%s', time='%s', referer='%s', username='%s']}",
				getClass(), getId(), getDate(), getTime(), getReferer(), getUserName());
	}

	public String getId() {
		return id;
	}

	public void setId(String key) {
		this.id = key;
	}



	public String getResourceId() {
		return resourceId;
	}



	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public String getReferer() {
		return referer;
	}



	public void setReferer(String referer) {
		this.referer = referer;
	}



	public String getRemoteAddr() {
		return remoteAddr;
	}



	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}



	public String getUserAgent() {
		return userAgent;
	}



	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}



	public String getMethod() {
		return method;
	}



	public void setMethod(String method) {
		this.method = method;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
