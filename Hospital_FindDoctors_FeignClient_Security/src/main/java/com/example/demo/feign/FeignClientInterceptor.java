package com.example.demo.feign;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.demo.pojos.MyToken;

import feign.RequestInterceptor;
import feign.RequestTemplate;
//uncomment @Component to register bean with container
//@Component
public class FeignClientInterceptor implements RequestInterceptor {

     private static final String AUTHORIZATION_HEADER = "Authorization";
       private static final String BEARER_TOKEN_TYPE = "Bearer";
    
    
       @Override
        public void apply(RequestTemplate template) {


   		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
   		map.add("username", "client1");
   		map.add("password", "client1");
   		map.add("grant_type", "client_credentials"); //
   		map.add("client_secret", "e3YNqtUjjG87K5DklLE1n6AdS6BsxYKJ");
   		map.add("client_id", "microservice_client");
   		map.add("scope", "openid");
   		HttpHeaders headers = new HttpHeaders();
   		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
   		HttpEntity<MultiValueMap<String, String>> requestBodyFormUrlEncoded = new HttpEntity<>(map, headers);
   		String access_token_url = "http://localhost:8080/realms/security_demo/protocol/openid-connect/token";
   		
   		RestTemplate restTemplate = new RestTemplate();
   		ResponseEntity<MyToken> response_token = restTemplate.exchange(access_token_url, HttpMethod.POST,
   				requestBodyFormUrlEncoded, MyToken.class);
   		System.out.println("obtained token in request intercceptor:-"+response_token.getBody().getAccess_token());
        template.header(AUTHORIZATION_HEADER,String.format("%s %s", BEARER_TOKEN_TYPE, response_token.getBody().getAccess_token()));
        }
}       