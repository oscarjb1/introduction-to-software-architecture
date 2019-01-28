package io.reactiveprogramming.crm.api.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.reactiveprogramming.crm.api.services.SecurityService;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityFilter implements Filter {
	
	@Autowired
	private SecurityService securityService;

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		if("/security/login".equals(request.getRequestURI())) {
			chain.doFilter(req, res);
		}
		
		String token = httpRequest.getHeader("Authorization");
		
		boolean requiredAuth = true;
		
		if(requiredAuth && token == null) {
			response.setStatus(401);
			response.getOutputStream().println("{\"ok\": false,\"message\": \"Unauthorized - Required token\"}");
			response.setContentType("application/json");
		}else {
			try {
				String username = securityService.decriptToken(token);
				chain.doFilter(req, res);
			} catch (Exception e) {
				response.setStatus(401);
				response.getOutputStream().println("{\"ok\": false,\"message\": \"Unauthorized - Required token\"}");
				response.setContentType("application/json");
			}
			
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}

}
