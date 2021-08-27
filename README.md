# phonebook-spring-app

## This Application represents a simple phonebook api for creating and searching a phonebook using API endpoints, developed using popular design patterns, providing modularity and reusability

## Technologies
* Spring Boot 2.5.4
* Java version 16
* PostgreSQL 13.4
* JUNIT 5

## Endpoints
This aplication has 3 endpoints
* GET: "/" : Calling this endpoint returns a list of all the clients in the phonebook
* GET: "/contacts" : This endpoint requires at least one of two query parameters: "name" and/or "phoneNumber". Calling this method returns a list of clients that contain the name query parameter in their name (if provided) and the client that has the phone number specified in the query parameter (if provided)
* POST: "/contacts" : This endpoint requires a JSON body with two attributes: "name" and "phoneNumber".
  Constraints:
    * The name attribute must not have a NULL or blank value (must have at least one character). 
    * The phoneNumber must be unique (else an error response will be returned).
    * The phoneNumber must follow the following pattern: +389 7 [0,1,5 or 6] XXX XXX (any digit replaces X). Example phone number is: +38971335897 (random numbers are selected).
    
    Calling this endpoint with a valid payload persists the client to the database and returns a confirmation response with a status of CREATED, along with the client object as the body of the response
    
## How to run
* Make sure you have PostgreSQL installed on your machine.
* After cloning this repository and downloading the Maven dependencies, edit the application.properties file so you have your own database username and password listed.
* Build and run the project.
Note: No need for previously creating the database schema, as in the application.properties, Hibernate is set to auto-create the database on startup and drop it on shutdown. (shown in the picture below).
![image](https://user-images.githubusercontent.com/66920815/131140554-ddcf4a62-8e63-43e5-a38e-87c97f63604a.png)

## Both Unit and Integration tests are provided in this repository, developed with JUNIT5 and Hamcrest
