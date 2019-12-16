package io.reactiveprogramming.gateway.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

@Configuration
public class DefaultFallback {
	@Bean
	public FallbackProvider zuulFallbackProvider() {
		return new FallbackProvider() {

			@Override
			public String getRoute() {
				return "*";
			}
			
			@Override
			public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
				return new ClientHttpResponse() {
					@Override
					public HttpStatus getStatusCode() throws IOException {
						return HttpStatus.OK;
					}

					@Override
					public int getRawStatusCode() throws IOException {
						return HttpStatus.OK.value();
					}

					@Override
					public String getStatusText() throws IOException {
						return HttpStatus.OK.toString();
					}

					@Override
					public void close() {
					}

					@Override
					public InputStream getBody() throws IOException {
						return new ByteArrayInputStream(
								"{\"ok\":false,\"message\":\"Oops, something went wrong, please try again or later\"}".getBytes());
					}

					@Override
					public HttpHeaders getHeaders() {
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						return headers;
					}
				};
			}
		};
	}
}
