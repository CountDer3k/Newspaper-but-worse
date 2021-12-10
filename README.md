# Newspaper-but-worse
---
A newspaper site, but much worse ;)

Built by the best group (and only group), Group 2, this newspaper site has only
some of the functionality you would typically expect from a Newspaper (and all 
of the Spiderman references), but none of the style.

## Docker Build Instructions
Change into the Newspaper-but-worse directory. Run the following commands:

```mvn clean install```

```docker-compose up --build```

## Debug Instructions
```mvnDebug spring-boot:run -Dagentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000```

Then, run the project in debug mode with your favorite Java IDE.
