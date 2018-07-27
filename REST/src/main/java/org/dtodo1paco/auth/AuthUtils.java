/**
 * 
 */
package org.dtodo1paco.auth;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pac
 *
 */
public class AuthUtils {
	
	/**
	 * Gets the username if present
	 * @param context 
	 * @return
	 */
	public static String getUserName(HttpServletRequest context) {
		return context.getUserPrincipal() != null ? context.getUserPrincipal()
				.getName() : null;
	}
	
}
