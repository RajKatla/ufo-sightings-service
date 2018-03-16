package com.example.ufosighting.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ufosighting.UfoSightingApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UfoSightingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UfoSightingIntegrationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void shouldReturn200WhenSendingRequestToGetAllUfoSightings() {

		final ResponseEntity<Object[]> responseEntity = testRestTemplate.getForEntity(createURLWithPort("/sightings"),
				Object[].class);
		final Object[] objects = responseEntity.getBody();
		final HttpStatus statusCode = responseEntity.getStatusCode();

		assertThat(statusCode.value(), equalTo(200));
		assertThat(objects.length, equalTo(20455));

	}

	@Test
	public void shouldReturn200WhenSendingRequestToSearchUfoSightingsByYearAndMonth() {

		final ResponseEntity<Object[]> responseEntity = testRestTemplate
				.getForEntity(createURLWithPort("/sightings?year=1995&month=10"), Object[].class);
		final Object[] objects = responseEntity.getBody();
		final HttpStatus statusCode = responseEntity.getStatusCode();

		assertThat(statusCode.value(), equalTo(200));
		assertThat(objects.length, equalTo(87));

	}

	@Test
	public void shouldReturn400WhenSendingEmptyYearAndMonthToSerach() {

		final ResponseEntity<Object[]> responseEntity = testRestTemplate
				.getForEntity(createURLWithPort("/sightings?year=&month="), Object[].class);
		final Object[] objects = responseEntity.getBody();
		final HttpStatus statusCode = responseEntity.getStatusCode();

		assertThat(statusCode.value(), equalTo(400));
		assertThat(objects.length, equalTo(0));

	}

	@Test
	public void shouldReturn400WhenSendingNullYearAndMonthToSerach() {

		final ResponseEntity<Object[]> responseEntity = testRestTemplate
				.getForEntity(createURLWithPort("/sightings?year=null&month=null"), Object[].class);
		final Object[] objects = responseEntity.getBody();
		final HttpStatus statusCode = responseEntity.getStatusCode();

		assertThat(statusCode.value(), equalTo(400));
		assertThat(objects.length, equalTo(0));

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
