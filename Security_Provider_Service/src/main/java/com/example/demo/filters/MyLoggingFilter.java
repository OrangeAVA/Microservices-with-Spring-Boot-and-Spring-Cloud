package com.example.demo.filters;

import java.net.URI;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.resource.HttpResource;
import org.springframework.web.server.ServerWebExchange;

import io.netty.buffer.ByteBufAllocator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MyLoggingFilter implements GlobalFilter {

	final Logger logger = LoggerFactory.getLogger(MyLoggingFilter.class);

//only post filters	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		 HttpHeaders  header=exchange.getRequest().getHeaders();
		 header.forEach((k, v) -> {
				System.out.println(k + "\t" + v);
			});
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			ServerHttpResponse httpResponse = exchange.getResponse();
			HttpHeaders headers = httpResponse.getHeaders();

			headers.add("post_header", "this header is added by post filter");
			logger.info("Post Global Filter");

			headers.forEach((k, v) -> {
				System.out.println(k + "\t" + v);
			});
		}));
	}

}