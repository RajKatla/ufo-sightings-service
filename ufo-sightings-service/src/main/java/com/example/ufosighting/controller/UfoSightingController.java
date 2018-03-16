package com.example.ufosighting.controller;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ufosighting.service.UfoSightingService;

@RestController
public class UfoSightingController {

	private static final String EMPTY = "";

	private static final String YEAR_PATTERN = "\\d{4}";

	private static final String MONTH_PATTERN = "\\d{2}";

	@Autowired
	private UfoSightingService ufoSightingService;

	// If this resource produces json media type then it will be easy to test using jsonPath
	// Instead of returning List<String> we could return List<UfoSighting> and jackson library
	// will automatically serialize this list as list of json string
	
	@RequestMapping(value = "/sightings", method = RequestMethod.GET)  
	public ResponseEntity<List<String>> getSightings() {               
		List<String> allUfoSightingsList = Collections.emptyList(); 
		allUfoSightingsList = ufoSightingService.getAllSightings();
		return getResponseEntity(allUfoSightingsList);
	}

	// year and month variables are mandatory so we should change to path variables instead of request params.
	// Instead of returning List<String> we could return List<UfoSighting> and jackson library
	// will automatically serialize this list as list of json string
	
	@RequestMapping(value = "/sightings", params = { "year", "month" }, method = RequestMethod.GET) 																									
	@ResponseBody
	public ResponseEntity<List<String>> searchSightings(@RequestParam(name = "year") final String year,
			@RequestParam(name = "month") final String month) {
		return processRequest(year, month);
	}

	/**
	 * This method validates the request parameters year and month.
	 * If parameters are valid then it returns response with status code 200 (OK) and filtered UfoSighting list. 
	 * If parameters are not valid then it returns status code 400 (Bad Request) with empty list
	 */
	
	private ResponseEntity<List<String>> processRequest(final String year, final String month) {
		List<String> filteredList = Collections.emptyList();
		if (validate(year, month)) {
			final int yearSeen = Integer.valueOf(year);
			final int monthSeen = Integer.valueOf(month);
			filteredList = ufoSightingService.search(yearSeen, monthSeen);
			return getResponseEntity(filteredList);
		}
		return new ResponseEntity<List<String>>(filteredList, HttpStatus.BAD_REQUEST);
	}

	/**
	 * This method is responsible for preparing response entity.
	 * If the UfoSighting list is empty then it prepares response with status code 404 (Not Found) and empty list then returns to the caller.
	 * If the UfoSighting list has data then it prepares response with status code 200 (OK) and list data then returns to the caller.  
	 * 
	 */
	private ResponseEntity<List<String>> getResponseEntity(final List<String> list) {
		ResponseEntity<List<String>> responseEntity;
		if (list != null && !list.isEmpty()) {
			responseEntity = new ResponseEntity<List<String>>(list, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<List<String>>(list, HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	/**
	 * This method is responsible for validating the year and month request parameters.
	 * 
	 */
	private boolean validate(final String year, final String month) {
		final Pattern yearPattern = Pattern.compile(YEAR_PATTERN);
		final Pattern monthPattern = Pattern.compile(MONTH_PATTERN);

		if ((year != null && year != EMPTY) && (month != null && month != EMPTY)) {
			if (yearPattern.matcher(year).matches() && monthPattern.matcher(month).matches()) {
				return true;
			}
		}
		return false;
	}

}
