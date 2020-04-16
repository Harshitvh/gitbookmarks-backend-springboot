package com.example.demo.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.models.RepositoryAPIDto;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GateWayService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${repo.url}")
	private String url;
	
	public ResponseEntity<RepositoryAPIDto> getRepos(String searchQuery){
		HttpEntity<Object> request = new HttpEntity<>(createHeader());
		try {
			log.info(buildUrl());
			return restTemplate.exchange(buildUrl(), HttpMethod.GET,request, RepositoryAPIDto.class);
		}
		catch (final HttpClientErrorException e) {
			log.error("Unable to get Results",e);
			return null;
		}

	}
	
	public HttpHeaders createHeader(){
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		return header;
	}
	
	public String buildUrl(){
		return 	UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("q", "language:php+language:javascript")
				.toUriString();
	}


}
