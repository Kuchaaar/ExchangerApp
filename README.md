## Table of contents

* [General info](#General-info)
* [Requirements](#Requirements)
* [Technologies](#Technologies)
* [Setup](#Setup)

## General info

[Spring Boot](http://projects.spring.io/spring-boot/) application about currency exchange rates. The application provide
user to get data for currency exchange.
The application is connected to [NBP API](http://api.nbp.pl) to fetch real-time currency exchange data and store in
database. To ensure data accuracy, the application is also connected to [HOLIDAYS API](https://date.nager.at/Api). It
fetches data from the NBP API only when there are no holidays in Poland.

### Endpoints: </br>

| Method | URL                                        | Description                                                                       |
|--------|--------------------------------------------|-----------------------------------------------------------------------------------|
| `GET`  | `/available_dates`                         | Retrieve all available dates                                                      |
| `GET`  | `/available_codes`                         | Retrieve all available codes                                                      |
| `GET`  | `/available_dates/currency`                | Retrieve all available dates for the given currency code                          |
| `POST` | `/currency/highest_rate_percentage_change` | Retrieve number of highest percent change for the given dates and number          |
| `POST` | `/excel/currency/all`                      | Retrieve excel with all currencies for the given dates                            |
| `POST` | `/excel/currency/by_one`                   | Retrieve excel for the given currency code and dates                              |
| `POST` | `/excel/currency/extensions`               | Retrieve excel for the given currency code and dates with with calculated average |

## Requirements

* [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
* [Maven 3.x](https://maven.apache.org/index.html)
* [Docker](https://www.docker.com/products/docker-desktop/)

## Technologies

### Project Created with:  </br>

### General:

* **Java 17** - The core programming language for building the project.
* **Spring Boot** - Facilitates the development of robust and scalable applications, providing a comprehensive framework
  for Java development.
* **apache.poi** - A library for reading and writing Microsoft Office documents, used for response currency exchange
  data.
* **quartz** - An open-source job scheduling library that adds enterprise-level features to your Java applications.

### Integration:

* **jollyday** - An integration for handling holidays, ensuring accurate currency exchange rates.
* **feign** - A declarative web service client used for simplifying API integration.
* **okhttp** - A HTTP client for making network requests.

### Documentation:

* **openapi** - Used for defining an API standard and generating documentation.
* **swagger** - A tool for designing, building, documenting, and consuming RESTful web services.

### Database:

* **hibernate** - An object-relational mapping framework for Java applications, facilitating interaction with the
  database.
* **JPA** - A specification for managing relational data in Java applications.
* **JDBC** - Enables Java applications to interact with databases.
* **MySQL** - A relational database management system used for storing currencies.

### Monitoring:

* **actuator** - Provides runtime information about the application, facilitating monitoring and management.

### Tests:

* **JUnit** - A widely used testing framework for Java applications.
* **Mockito** - A mocking framework for creating and configuring mock objects.
* **mockserver** - Facilitates mocking of HTTP and HTTPS system for testing purposes.
* **testcontainers** - Enables the use of Docker containers for testing, ensuring a consistent environment.

### Build tools:

* **Maven** - A build automation tool for managing dependencies and building Java projects.
* **docker** - Used for containerization, ensuring that the application runs consistently across different environments.
* **docker-compose** - Facilitates the definition and running of multi-container Docker applications.

## Setup

1. Clone this repository
2. Go to the root directory of the repository: ``` cd ./exchanger```
3. Run ```docker-compose.yml```
4. Run the ```package``` Maven task ```mvn clean install```
5. Run the generated JAR file: ```java - jar <JAR-FILE>```

To view the generated Swagger UI documentation go to: http://localhost:8080/swagger-ui.html

To view the actuator status go to : https://localhost:8080/actuator
