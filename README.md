Creates a Spring Boot application that exposes a REST API but uses a Kafka repository as its data layer.
Importantly, it uses the `InteractiveQueryService` object to do this i.e. it treats the underlying topic
as though its a relational/non-relational database.

The app is configured to be monitored by DataDog too (to illustrate how to do this), and uses Jib to build
its Docker image.