package com.srtech.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.srtech.dto.CurrencyRequest;
import com.srtech.dto.CurrencyResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CurrencyConverterClient {

	private String uri = "http://localhost:8081/api/v1/currency/convert";

	private RestTemplate restTemplate = new RestTemplate();

	public ResponseEntity<CurrencyResponse> convertCurrency(CurrencyRequest currencyRequest) {
		ResponseEntity<CurrencyResponse> currencyResponse = restTemplate.postForEntity(uri, currencyRequest,
				CurrencyResponse.class);
		log.info("Status : {}",currencyResponse.getStatusCode());
		log.info("Body : {}",currencyResponse.getBody());
		return currencyResponse;

	}
}
