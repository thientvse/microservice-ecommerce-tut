# Microservice Demo Project

## Introduction
This project is a demonstration of a microservices architecture using **Spring Boot** and **Spring Cloud**. The system consists of three main services:

- **Order Service**: Manages orders
- **Inventory Service**: Manages inventory
- **Product Service**: Manages products

The project incorporates modern technologies to ensure availability, scalability, and system security.

## Tech Stack
- **Spring Boot**: Main framework for building services
- **Spring Cloud Gateway**: API Gateway for routing and security
- **OpenFeign**: API communication between services
- **Flyway**: Database versioning management
- **Testcontainer**: Integration testing with containerized environments
- **Keycloak**: User authentication and authorization
- **Resilience4j**: Circuit breaker for system stability
- **Kafka**: Message queue system for event handling
- **Grafana Stack**: Monitoring and logging

## System Architecture
The system follows a **Microservices** architecture with the following key components:

- **Spring Cloud Gateway** as the API Gateway
- **Keycloak** for authentication
- **Kafka** for inter-service communication
- **Grafana Stack** for system monitoring