# Newspaper-but-worse
---
Built by the best group (and only group), Group 2, this newspaper site has only
some of the functionality you would typically expect from a Newspaper (and all 
of the Spiderman references), but none of the style.

Fall 2021 - 4230 Capstone Project

By: Der3k, Jack, & Jazzley.

Version: 0.8.3-Beta-5

A newspaper/blog server-side site, built in Java.



## Technology
The technology used:
* Docker 
* MySQL/MariaDB
* Java
* Spring Boot
* Thymeleaf

## Purpose:
The goal of this project was to create a fully-functional server-side site. The main work was done on implementing the back end, database & having full docker compatibility for maximum cross-platform functionality.

The front-end design (UI/UX) are a planned for a future update to this project, including:
* New Theme
* Light/Dark Themes
* Mobile friendly

## How To run
### Method 1: Docker compose

Navigate to the directory and run:
'''
mvn clean install; docker-compose up --build
'''

### Method 2: Debug Mode
//Debug command:
mvnDebug spring-boot:run -Dagentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000










*A newspaper site, but much worse ;)*
