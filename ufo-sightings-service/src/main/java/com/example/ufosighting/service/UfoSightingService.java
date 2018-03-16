package com.example.ufosighting.service;

import java.util.List;

public interface UfoSightingService {


    /**
     * Returns all the sightings in the tsv file.
     */
	// Method return type should change to List<UfoSighting>
	// Should add throw keyword to throw exceptions those are thrown in the process of file read and data format and any other application related custom exceptions 
    List<String> getAllSightings(); 

    /**
     * Search for sightings happened in the specified year and month;
     *
     * @param yearSeen  The year when the sighting happened
     * @param monthSeen The month when the sightings happened
     */
    
	// Method return type should change to List<UfoSighting>
	// Should add throw keyword to throw exceptions those are thrown in the process of file read and data format and any other application related custom exceptions
    List<String> search(int yearSeen, int monthSeen);


}
