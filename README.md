# Weather App

## Overview
This project is built using JDK 17, Jakarta EE 10, and Vaadin 24.3.3.

## Prerequisites
- JDK 17
- Docker
- Docker Compose
- Maven

## Setup Instructions
1. Clone the repository.

2. Start PostgreSQL using Docker Compose:
    ```bash
    docker-compose up -d
    ```

3. Build and deploy the project using Maven and WildFly:
    ```bash
    mvn wildfly:run
    ```

4. Access the application:
    - Once the project is successfully deployed, you can access it at [http://localhost:8080/register](http://localhost:8080/register).
