package io.reactiveprogramming.gateway.filter;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import io.reactiveprogramming.commons.dto.LoginResponseDTO;
import io.reactiveprogramming.commons.rest.WrapperResponse;


public class SecurityFilter extends ZuulFilter {
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		String path = request.getRequestURI();
		if("/api/security/login".equals(path) 
				|| "/api/security/login".equals(path)
				|| "/api/security/sso".equals(path)
				|| "/api/security/loginForm".equals(path)
				|| path.startsWith("/api/crm/products/thumbnail")
				) {
			//Handle all public resources
		}else {
			String token = request.getHeader("Authorization");
			if (token == null) {
				ctx.setResponseBody("{\"ok\": false,\"message\": "
						+"\"Zuul Unauthorized - Required token\"}");
	            ctx.getResponse().setContentType("application/json");
	            ctx.setResponseStatusCode(401);
			}
			
			Map<String,String> queryParams = new HashMap<>();
			queryParams.put("token", token);
			
			class LoginWrapper extends WrapperResponse<LoginResponseDTO>{};
			WrapperResponse<LoginResponseDTO> result = 
					restTemplate.exchange(
							"http://security/token/validate?token={token}", 
					HttpMethod.GET,	null, 
					new ParameterizedTypeReference
						<WrapperResponse<LoginResponseDTO>>() {}, 
					queryParams).getBody();
			
			if(!result.isOk()) {
				ctx.setResponseBody("{\"ok\": false,\"message\": \""
						+result.getMessage()+"\"}");
	            ctx.getResponse().setContentType("application/json");
	            ctx.setResponseStatusCode(401);
			}
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}
}
