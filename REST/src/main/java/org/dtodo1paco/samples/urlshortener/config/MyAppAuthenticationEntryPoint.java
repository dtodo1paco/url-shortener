package org.dtodo1paco.samples.urlshortener.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
@Component
public class MyAppAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	
	public static final String REALM = "urlShortener REALM";
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		     AuthenticationException authException) throws IOException {
        if(isPreflight(request)){
        	response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    		response.setHeader("Access-Control-Allow-Headers", "Authorization");
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
        	response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");    		
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());       	
        }
	}
	@Override
	public void afterPropertiesSet()  {
		setRealmName(MyAppAuthenticationEntryPoint.REALM);
	}
    /**
     * Checks if this is a X-domain pre-flight request.
     * @param request
     * @return
     */
    private boolean isPreflight(HttpServletRequest request) {
        return "OPTIONS".equals(request.getMethod());
    }
}
