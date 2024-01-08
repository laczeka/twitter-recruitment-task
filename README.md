# Twitter like - recruitment task

Build a simple social networking application, similar to Twitter, and expose it through a web API. The application should support the scenarios below.

## Use cases

### Posting

A user should be able to tweet a 140 character message.

### Wall

A user should be able to see a list of the messages they've posted, in reverse chronological order.

### Following

A user should be able to follow another user. Following doesn't have to be reciprocal: Alice can follow Bob without Bob having to follow Alice.

### Timeline

A user should be able to see a list of the messages posted by all the people they follow, in reverse chronological order.

## Registering users

Registering users: a user is created as soon as they tweet their first message
I want to show rich model domain approach so I have added requirement that user login can be normalized like Gmail login, also can't be swear word.
Tweets also can be validated, but i create validation only for author logins to show rich model domain approach.

## Out of scope:

- user authentication
- frontend (only backend provided)
- storage (storing everything in memory is fine)

### Basic info
- SpringBoot 3.2
- Java 17
- H2 in-memory database
- Maven

### Code coverage:
- Jacoco: 94%

Swagger UI URL: http://localhost:8080/swagger-ui/index.html

H2 console URL: http://localhost:8080/h2-console

DB URL and authorization credentials can be found in application.yaml file.

To run the app execute command:
>./mvnw spring-boot:run
 
### Nest steps:
- we can think about adding more business logic and use Rich model domain/DDD approach
- use port and adapters
- add real database and use Testcontainers in test (instead of H2)
- if needed add async communication to other domains