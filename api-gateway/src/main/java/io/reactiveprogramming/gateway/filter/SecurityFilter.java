package io.reactiveprogramming.gateway.filter;


import javax.servlet.http.HttpServletRequest;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class SecurityFilter extends ZuulFilter {

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		if (request.getHeader("Authorization") == null) {
			throw new ZuulException("No autorizado", 401, "Es requerido un token de autenticación");
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
