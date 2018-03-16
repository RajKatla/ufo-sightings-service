# UFO Sightings Service

We have a huge TSV file >100MB (part of it is included in the project resources folder) and we need to create a microservice to serve the information inside it.

We have provided a simple interface (UfoSightingService) and would like you provide an implementation for it and serve it through REST 

  

## Objectives
 
### 1. Create an application to expose both endpoints described below

We provided a basic Spring Boot skeleton but you can use any other framework you like

The expectation is that we can checkout the application, build it with `mvn clean package` and run it as a uber jar (`java -jar) 
 
#### 1.1 Returns all the sightings    
    
    GET /sightings 
    
#### 1.2 Search for sightings where `UfoSighting.getDateSeen()` is October 1995

    GET /sightings?year=1995&month=10 

**Example usage:** 
- Searching the sighting happened on October `1995` should return `107` sightings
- Searching all the sightings should return `61391` results 


### 2. Optimize it to be as fast as it can be

Given that we don't have any memory constraint we want the search (as described in 1.2) should to happen as quickly as possible

## Constraints
- UfoSighting DTO and UfoSightingService interface cannot be changed
- The TSV File cannot be altered

## What are we looking for

- Clean code
- Unit testing
- Integration testing
- Your comments on what you would change on the interface and DTO


## Suggestions
Keep it simple
You can use any Java version

You can use any library available on internet but none should be necessary


### How to submit the code

Fork this repository and send us a pull request when you are happy to submit your implementation for review. 

**OR** via mail but make sure to

Clean the project (don't submit the target folder)

Remove the tsv file (Limited inbox, sorry)
