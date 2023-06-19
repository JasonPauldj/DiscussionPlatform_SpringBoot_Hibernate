# DiscussionPlatform_SpringBoot_Hibernate

## Application Overview
- This is a SpringBoot application that serves as the backend for a discussion platform.([Link to Frontend](https://github.com/JasonPauldj/DiscussionPlatform_ReactJS)). 
- It provides a REST API for users to ask questions, post answers, and comments. 
- The app uses Hibernate ORM to store and retrieve data from the database. 
- To ensure secure user authentication, the app leverages Spring Security and implements a JWT Token-based authentication system.
- Deployed the application on Amazon Elastic Container Service (ECS) via Terraform as IaC.([Link to Infrastructure](https://github.com/JasonPauldj/DiscussionPlatform_Infra)).

## Docker Overview
- Utilized Docker to containerize the application and database. 
- Deployed the application using Docker Compose locally for development purpose
- Leveraged Docker Swarm for container orchestration in AWS EC2 instances

## Github Action Overview
- GitHub Action gets triggered on push to main branch which builds and pushes the image to Docker Hub.

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

## Applicaiton and DB in 2 different Containers on a single Docker Host
- To create a simple bridge network:
`docker network create dp-net`
- To start MySQL instance in a Docker Container:
`docker run --name dp-db --network dp-net -e MYSQL_ROOT_PASSWORD=****** -d mysql`
- To build the Image with the application jar:
`docker build -t dp-app .`
- To start the Application in a Docker Container: `docker run --network dp-net -d -p 8080:8080 -e hibernate_connection_url="***" -e hibernate_connection_password="***" -e hibernate_connection_username="***"  dp_app`

## Applicaiton and DB in 2 different Containers on a single Docker Host using Docker Compose
- For starting the Application and DB using Docker Compose: `docker compose up --build`

## Running Applicaiton in Docker Swarm Mode (Multiple Docker Node) - Imperatively
- For simulating mulitple docker nodes, install Docker on Ubuntu EC2 instances. [Install Docker Enginer on Ubuntu](https://docs.docker.com/engine/install/ubuntu/)
- To initialize Docker Swarm:
`sudo docker swarm init --advertise-addr <IP Address of Host>:2377 --listen-addr <IP Address of Host>:2377`
- To join Docker Swarm as Worker:
`docker swarm join --token <token> <IP Address of Manager Host>:2377 --advertise-addr <IP Address of Worker Host>:2377 --listen-addr <IP Address of Worker Host>:2377`
- To create the service:
`sudo docker service create --name dp-app -p 8080:8080 --replicas 3 jasonpaulneu/dp`
<br>
**NOTE :** `jasonpaulneu/dp` is an image pushed to Docker hub repository
<br>
**NOTE :** Be mindful of the platform being used for building the Docker Image. For Ubuntu EC2 the platform is amd64
