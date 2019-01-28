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
		System.out.println("zuul getRequestURI ==> " + request.getRequestURI());
		if("/api/security/login".equals(request.getRequestURI()) 
				|| "/api/security/login".equals(request.getRequestURI())
				|| "/api/security/sso".equals(request.getRequestURI())
				|| "/api/security/loginForm".equals(request.getRequestURI())
				) {
			
		}else {
			String token = request.getHeader("Authorization");
			System.out.println("Token ==> " + token);
			if (token == null) {
				ctx.setResponseBody("{\"ok\": false,\"message\": \"Zuul Unauthorized - Required token\"}");
	            ctx.getResponse().setContentType("application/json");
	            ctx.setResponseStatusCode(401);
			}
			
			Map<String,String> queryParams = new HashMap<>();
			queryParams.put("token", token);
			
			class LoginWrapper extends WrapperResponse<LoginResponseDTO>{};
			//WrapperResponse result = restTemplate.getForObject("http://security/token/validate?token={token}", WrapperResponse.class, queryParams);
			WrapperResponse<LoginResponseDTO> result = restTemplate.exchange("http://security/token/validate?token={token}", HttpMethod.GET, null, new ParameterizedTypeReference<WrapperResponse<LoginResponseDTO>>() {}, queryParams).getBody();
			
			if(!result.isOk()) {
				ctx.setResponseBody("{\"ok\": false,\"message\": \""+result.getMessage()+"\"}");
	            ctx.getResponse().setContentType("application/json");
	            ctx.setResponseStatusCode(401);
			}
			System.out.println("Token validate => " + ReflectionToStringBuilder.toString(result, ToStringStyle.MULTI_LINE_STYLE));
			System.out.println(result.getBody().getEmail());
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
