# DiscussionPlatform_SpringBoot_Hibernate

## Overview
This is a SpringBoot application that serves as the backend for a discussion platform.([Link to Frontend](https://github.com/JasonPauldj/DiscussionPlatform_ReactJS)). It provides a REST API for users to ask questions, post answers, and comments. The app uses Hibernate ORM to store and retrieve data from the database. To ensure secure user authentication, the app leverages Spring Security and implements a JWT Token-based authentication system.

## Versions

| Technology | Version |
| --- | ----------- |
| Java | 17 |
| SpringBoot | 3.0.5 |
| Spring Framework | 6.0.7 |
| Hibernate | 6.1.7 |
| Jakarta Persistence | 3.1 |
| Tomcat | 10.1.7 |
| MySQL | 8.0.31 |

## Docker Commands
- To create a simple bridge network:
`docker network create dp-net`
- To start MySQL instance in a Docker Container:
`docker run --name dp-db --network dp-net -e MYSQL_ROOT_PASSWORD=****** -d mysql`
- To build the Image with the application jar:
`docker build -t dp-app .`
- To start the Application in a Docker Container: `docker run --network dp-net -d -p 8080:8080  dp_app`
- For starting the Application and DB using Docker Compose: `docker compose up --build`
