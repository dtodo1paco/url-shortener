/**
 * 
 */
package org.dtodo1paco.samples.urlshortener.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

/**
 * @author pac
 *
 */


@Component
public class HerokuContainer implements EmbeddedServletContainerCustomizer {
 
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		String port = System.getenv("PORT");
		if (port != null) {
			container.setPort(Integer.parseInt(port));
			try {
				container.setAddress(InetAddress.getByName("0.0.0.0"));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}		
		}
	} 
}