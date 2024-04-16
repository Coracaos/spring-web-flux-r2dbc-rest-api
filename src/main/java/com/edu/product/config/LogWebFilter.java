package com.edu.product.config;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Configuration
public class LogWebFilter implements WebFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		var startTime = Instant.now();
		var correlationId = UUID.randomUUID().toString();
		var request = exchange.getRequest();
		var method = request.getMethod().name();
		var path = request.getPath().value();
		log.info("correlationId={}, method={}, path={}", correlationId, method, path);
		return chain.filter(exchange).doFinally( s -> {
			var time = Duration.between(startTime, Instant.now()).toMillis();
			var code = exchange.getResponse().getStatusCode().value();
			var reason = HttpStatus.resolve(code).getReasonPhrase();
			log.info("correlationId={}, code={}, reason={}, time={}", correlationId, code, reason, time);
		});
	}

}
