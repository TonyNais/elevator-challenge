# ELEVATOR-CHALLENGE

## DATABASE
The Application run on H2 in-memory database, the configuration can be edited on the properties to run on mysql or oracle databases.

## ABOUT
This webservice provides APIs to control and monitor an elevator system.

It provides APIs to call an elevator from any floor to any floor and to monitor the status/location of the elevators.

It ensures that the elevators move asynchronously and operation of one elevator doesn't affect the operation of another.

The application has JUnit5 unit tests. To test simply run `mvn clean test` on your commandline

## HOW TO RUN
Run the springboot application (`mvn spring-boot:run`)

Navigate to http://localhost:8080/swagger-ui/index.html on your browser for the detailed API documentation