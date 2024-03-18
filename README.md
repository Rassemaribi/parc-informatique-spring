# parc-informatique-spring


## Requirements

For building and running the application you need:

- [JDK 21](https://www.oracle.com/java/technologies/downloads/#jdk21-windows)
- [Maven 4](https://maven.apache.org)
- [MySQL 8](https://dev.mysql.com/downloads/mysql/)

## Running the application locally

Intellij IDEA is the best IDE to run this application.

(https://www.jetbrains.com/idea/download/)

## Setting Up Database Roles

After running the application, you'll need to execute the following SQL commands to set up database roles:

```sql
INSERT INTO roles(name) VALUES('ROLE_COLLABORATEUR');
INSERT INTO roles(name) VALUES('ROLE_DSI');
```



## Copyright

Copyright © 2024 Freedom Of Dev Services.